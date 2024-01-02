package com.alphacircle.vroadway.util

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class AssetManager(
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
    private val externalPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    suspend fun downloadFile(
        url: String,
        contentId: Int,
        fileName: String,
        setIsDownloading: (Boolean) -> Unit,
        setDownloadProgress: (Float) -> Unit,
        setIsDownloadFinished: (Boolean) -> Unit
    ) {
        Log.println(Log.DEBUG, "AssetDownloader", "${isFolderExist(contentId)}")
        when {
            isDownloading -> cancelDownload(fileName, contentId, setIsDownloading, setDownloadProgress)
            else -> startDownload(url, contentId, fileName, setIsDownloading, setDownloadProgress, setIsDownloadFinished)
        }
    }

    private fun cancelDownload(fileName: String,
                               contentId: Int,
                               setIsDownloading: (Boolean) -> Unit,
                               setDownloadProgress: (Float) -> Unit) {
        isDownloadFinished = true
        downloadManager.remove(downloadId)
        Log.println(Log.DEBUG, "AssetDownloader", "❎")

        if(!fileName.contains("acc")) return
        deleteAssets(contentId)

        MainScope().launch {
            setIsDownloading(false)
            setDownloadProgress(0f)
            Toast.makeText(context, "Cancel Download", Toast.LENGTH_SHORT).show()
        }

        isDownloading = false
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
        MainScope().launch {
            setIsDownloading(true)
            Toast.makeText(context, "Start downloading: $fileName", Toast.LENGTH_SHORT).show()
        }

        val folder = File(externalPath, "/$contentId/")
        folder.mkdirs()

        val request = DownloadManager.Request(Uri.parse(url))
            .setDestinationUri(Uri.fromFile(File(folder, fileName)))
//            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)


        downloadId = downloadManager.enqueue(request)

        while (!isDownloadFinished) {
            val cursor: Cursor =
                downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToFirst() && fileName.contains("acf")) {
                isDownloading = true

                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        isDownloading = false
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
                        isDownloading = false
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
    fun deleteAssets(contentId: Int) {
        if(isFolderExist(contentId)) File(externalPath, "$contentId").deleteRecursively()
    }

    fun isFolderExist(contentId: Int): Boolean {
        File(externalPath, "").listFiles().map { it -> Log.println(Log.DEBUG, "AssetDownloader", "🌶️${it.absolutePath}") }
        Log.println(Log.DEBUG, "AssetDownloader", "$contentId exist? ❤️${File(externalPath, "$contentId").exists()}")
        return File(externalPath, "$contentId").exists()
    }

    fun getTotalFileSize(contentId: Int): Long {
        return when {
            isFolderExist(contentId) -> File(externalPath, "$contentId").listFiles().sumOf { it.length() }
            else -> 0L
        }
    }
}