/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alphacircle.vroadway

import android.content.Context
import androidx.room.Room
import com.alphacircle.vroadway.data.PodcastsFetcher
import com.alphacircle.vroadway.data.PodcastsRepository
import com.rometools.rome.io.SyndFeedInput
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.LoggingEventListener

/**
 * A very simple global singleton dependency graph.
 *
 * For a real app, you would use something like Hilt/Dagger instead.
 */
object Graph {
    lateinit var okHttpClient: OkHttpClient

    private val syncFeedInput by lazy { SyndFeedInput() }

    val podcastRepository by lazy {
        PodcastsRepository(
            podcastsFetcher = podcastFetcher,
            mainDispatcher = mainDispatcher
        )
    }

    private val podcastFetcher by lazy {
        PodcastsFetcher(
            okHttpClient = okHttpClient,
            syndFeedInput = syncFeedInput,
            ioDispatcher = ioDispatcher
        )
    }

    private val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    private val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    fun provide(context: Context) {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), (20 * 1024 * 1024).toLong()))
            .apply {
                if (BuildConfig.DEBUG) eventListenerFactory(LoggingEventListener.Factory())
            }
            .build()
    }
}
