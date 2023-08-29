package com.alphacircle.vroadway.util

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream

interface Downloader {
    fun downloadFile(url: String, videoTitle: String, downloadStateChange: (Boolean) -> Unit)
}

class VideoDownloader(
    private val context: Context
) : Downloader {
    var downloadId: Long = -1
    var isPaused: Boolean = false
    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    private val downloadCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                // 다운로드 완료 처리
            }
        }
    }

    override fun downloadFile(
        url: String,
        videoTitle: String,
        downloadStateChange: (Boolean) -> Unit
    ) {
        when {
            isPaused -> resumeDownload()
//            else -> startDownload(url, videoTitle)
        }
    }

    @SuppressLint("Range")
    suspend fun startDownload(
        url: String,
        contentId: Int,
        fileName: String,
        setIsDownloading: (Boolean) -> Unit,
        setDownloadProgress: (Float) -> Unit,
        setIsDownloadFinished: (Boolean) -> Unit
    ) {
//        val downloadUrl =
//            "https://dev-xion.s3.ap-northeast-2.amazonaws.com/JHOPE.mp4" // 다운로드할 동영상 URL
        // DownloadManager로 비디오 다운로드
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val externalFilesContentFolderDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) /** externalFilesDir: /storage/emulated/0/Android/data/com.alphacircle.vroadway/files/Download/{contentId} */
        val folder = File(externalFilesContentFolderDir, "/$contentId/")

        Toast.makeText(context, "Start downloading: $fileName", Toast.LENGTH_SHORT).show()

        val request = DownloadManager.Request(Uri.parse(url))
            .setDestinationUri(Uri.fromFile(File(folder, fileName)))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)


        val downloadId = downloadManager.enqueue(request)

        var isDownloadFinished = false

        while (!isDownloadFinished) {
            val cursor: Cursor =
                downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToFirst()) {
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        isDownloadFinished = true
                        setIsDownloadFinished(true)
//                        emit(State.run { error(context.getString(R.string.all_error_download_failed)) })
                    }

                    DownloadManager.STATUS_PAUSED -> {
                    }

                    DownloadManager.STATUS_PENDING -> {
                    }

                    DownloadManager.STATUS_RUNNING -> {
                        val totalSizeInBytes: Long =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (totalSizeInBytes >= 0) {
                            val downloadedBytes: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            val progress = downloadedBytes.toFloat() / totalSizeInBytes
                            setDownloadProgress(progress)
                            Log.println(Log.DEBUG, "Downloader♒️", "progress: $progress")

//                            emit(State.downloading(downloadProgress))
                        }
                        delay(1000)
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        setDownloadProgress(1.0f)
//                        emit(State.downloading(downloadProgress))
                        isDownloadFinished = true
                        setIsDownloading(false)
                        setIsDownloadFinished(true)

                        Toast.makeText(context, "Video saved: $fileName", Toast.LENGTH_SHORT).show()
//                        emit(State.success(true))
                    }
                }
            }
        }

//        downloadManager.enqueue(
//            DownloadManager.Request(Uri.parse(url)).apply {
//                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                setDestinationInExternalFilesDir(context, null, videoTitle)
//            }
//        )
    }

    fun isFolderExist(contentId: Int): Boolean {
        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//        val folders = File(externalFilesDir, "").listFiles()
//        return folders.any { it.isDirectory && it.name == contentId.toString() }
        return File(externalFilesDir, contentId.toString()).exists()
    }


    fun pauseDownload() {
        downloadManager.remove(downloadId)

        isPaused = false
    }

    @SuppressLint("Range")
    fun resumeDownload() {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        if (cursor != null && cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

            if (status == DownloadManager.STATUS_PAUSED) {
//                val downloadUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
//                val resumeDownloadId = downloadManager.remove(downloadId)
//
//                // 이어받기를 시작합니다.
//                saveDownloadIdToPreferences(resumeDownloadId)

                val uri =
                    Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                val fileUri = Uri.parse(uri.path)
                val resumeDownloadRequest = DownloadManager.Request(fileUri)
                downloadId = downloadManager.enqueue(resumeDownloadRequest)
            }
        }

        cursor?.close()
    }

    private fun getSavedDownloadIdFromPreferences(): Long {
        val preferences = context.getSharedPreferences("DownloadPrefs", Context.MODE_PRIVATE)
        return preferences.getLong("DownloadId", -1L) // return -1L by default value
    }

    private fun saveDownloadIdToPreferences(downloadId: Long) {
        val preferences = context.getSharedPreferences("DownloadPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putLong("DownloadId", downloadId)
        editor.apply()
    }


    @SuppressLint("Range")
    fun getDownloadProgress(): Float {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        var progress = 0f

        if (cursor != null && cursor.moveToFirst()) {
            val downloadedBytes =
                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val totalBytes =
                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

            progress = (downloadedBytes * 100 / totalBytes).toFloat()
        }

        cursor?.close()

        if (progress > 100) {
            downloadId = -1
        }

        return progress
    }

    fun unregisterReceiver() {
        try {
            context.unregisterReceiver(downloadCompleteReceiver)
        } catch (e: IllegalArgumentException) {
            // 예외 처리: 리시버가 이미 등록 해제된 경우
        }
    }

    // 외부 저장소에 비디오 저장
    fun saveVideo(videoUrl: String, videoTitle: String) {
        Toast.makeText(context, "Start downloading: $videoTitle", Toast.LENGTH_SHORT).show()
        // 외부 저장소에 비디오를 저장할 디렉토리 생성
        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        externalFilesDir?.mkdirs()

        // 비디오 파일을 생성
        val videoFile = File(externalFilesDir, videoTitle)

        // 비디오를 다운로드
        val inputStream = context.contentResolver.openInputStream(Uri.parse(videoUrl))
        val outputStream = FileOutputStream(videoFile)

        var downloadedBytes = 0L
        while (true) {
            // 읽을 수 있는 바이트 수만큼 다운로드
            val buffer = ByteArray(2048)
            val bytesRead = inputStream?.read(buffer) ?: -1


            if (bytesRead == -1) {
                // 다운로드가 완료되었으면 종료
                break
            }

            // 다운로드한 바이트 수를 업데이트
            downloadedBytes += bytesRead

            // 비디오 파일에 다운로드한 데이터를 기록
            outputStream.write(buffer, 0, bytesRead)
        }

        // 비디오 파일을 닫음
        inputStream?.close()
        outputStream.close()
        Toast.makeText(context, "Video saved: $videoTitle", Toast.LENGTH_SHORT).show()
    }

}