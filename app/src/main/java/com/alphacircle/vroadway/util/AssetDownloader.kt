package com.alphacircle.vroadway.util

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class AssetDownloader(
    private val context: Context
) {
    var downloadId: Long = -1
    var isDownloading: Boolean = false
    var isDownloadFinished: Boolean = false

    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    /**
     *  externalFilesDir: /storage/emulated/0/Android/data/com.alphacircle.vroadway/files/Download/{contentId}
     **/
    private val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    suspend fun downloadFile(
        url: String,
        contentId: Int,
        fileName: String,
        setIsDownloading: (Boolean) -> Unit,
        setDownloadProgress: (Float) -> Unit,
        setIsDownloadFinished: (Boolean) -> Unit
    ) {
        when {
            isFolderExist(contentId) -> cancelDownload(setIsDownloading, setDownloadProgress)
            else -> startDownload(url, contentId, fileName, setIsDownloading, setDownloadProgress, setIsDownloadFinished)
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
        Log.println(Log.DEBUG, "AssetDownloader", "1️⃣")
        isDownloading = true
        setIsDownloading(true)
        // DownloadManager로 비디오 다운로드
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val externalFilesContentFolderDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val folder = File(externalFilesContentFolderDir, "/$contentId/")

        MainScope().launch {
            Toast.makeText(context, "Start downloading: $fileName", Toast.LENGTH_SHORT).show()
        }

        val request = DownloadManager.Request(Uri.parse(url))
            .setDestinationUri(Uri.fromFile(File(folder, fileName)))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)


        downloadId = downloadManager.enqueue(request)

        isDownloadFinished = false

        while (!isDownloadFinished) {
            Log.println(Log.DEBUG, "AssetDownloader", "2️⃣")
            val cursor: Cursor =
                downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToFirst() && fileName.contains("acf")) {
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        isDownloadFinished = true
                        MainScope().launch { setIsDownloadFinished(true) }
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
                            MainScope().launch { setDownloadProgress(progress) }
                            delay(1000)
                        }
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        isDownloadFinished = true
                        MainScope().launch {
                            Toast.makeText(context, "Video saved: $fileName", Toast.LENGTH_SHORT).show()
                            setIsDownloading(false)
                            setIsDownloadFinished(true)
                        }
                    }
                }
            }
        }
    }

    fun isFolderExist(contentId: Int): Boolean {
        return File(externalFilesDir, contentId.toString()).exists()
    }


    fun cancelDownload(setIsDownloading: (Boolean) -> Unit,
                       setDownloadProgress: (Float) -> Unit) {
        downloadManager.remove(downloadId)
        Log.println(Log.DEBUG, "AssetDownloader", "❎")

        MainScope().launch {
            setIsDownloading(false)
            setDownloadProgress(0f)
            Toast.makeText(context, "Cancel Download", Toast.LENGTH_SHORT).show()
        }

        isDownloading = false
        isDownloadFinished = true
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

                val uri =
                    Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                val fileUri = Uri.parse(uri.path)
                val resumeDownloadRequest = DownloadManager.Request(fileUri)
                downloadId = downloadManager.enqueue(resumeDownloadRequest)
            }
        }

        cursor?.close()
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