package com.alphacircle.vroadway.util

import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.ln
import kotlin.math.pow

/**
 * Calculate Running Time of Video
 */
fun runningTimeConverter(seconds: Long): String {
    val duration = Duration.ofSeconds(seconds)
    val instant = Instant.ofEpochMilli(duration.toMillis())
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneOffset.UTC)
    return formatter.format(instant)
}

/**
 * Calculate File Size of Video
 */
fun fileSizeConverter(bytes: Long): String {
    val unitSymbols = arrayOf("Bytes", "KB", "MB", "GB", "TB")

    if (bytes == 0L) return "0Bytes"

    val i = (ln(bytes.toDouble()) / ln(1024.0)).toInt()
    val fileSize = bytes / 1024.0.pow(i.toDouble())
    return "${fileSize.toInt()}${unitSymbols[i]}"
}
