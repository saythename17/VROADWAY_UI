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

package com.alphacircle.vroadway.ui.home.category

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.LowLevelCategory
import com.alphacircle.vroadway.util.RetrofitService
import com.alphacircle.vroadway.util.AssetDownloader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VRCategoryContentViewModel(
    private val categoryId: Long,
) : ViewModel() {
    private val _state = MutableStateFlow(PodcastCategoryViewState())

    val state: StateFlow<PodcastCategoryViewState>
        get() = _state

    init {
        viewModelScope.launch {
            RetrofitService.getContents(categoryId,
                onSuccess = {
                    Log.println(Log.DEBUG, "VRCategory", it.toString())
                    val contents = it
                    if (it.isNotEmpty()) _state.update {
                        PodcastCategoryViewState(
                            contents = contents
                        )
                    }
                })
        }
    }

    fun onCategorySelected(categoryId: Int, index: Int) {
        RetrofitService.getContents(categoryId.toLong(), onSuccess = { it ->
            val contents = it
            if (it.isNotEmpty()) _state.update {
                PodcastCategoryViewState(
                    contents = contents,
                    categoryIndex = index
                )
            }
            Log.println(
                Log.DEBUG,
                "VRCategory",
                "categoryID= $categoryId, categoryIndex= ${_state.value.categoryIndex}"
            )
        })
    }

    fun onClickDownload(
        contentId: Int,
        context: Context,
        setIsDownloading: (Boolean) -> Unit,
        setDownloadProgress: (Float) -> Unit,
        setIsDownloadFinished: (Boolean) -> Unit
    ) {
        RetrofitService.getAssets(contentId, onSuccess = { it ->
            it.map {
                GlobalScope.launch() {
                    AssetDownloader(context).downloadFile(
                        it.location,
                        contentId,
                        it.name,
                        setIsDownloading,
                        setDownloadProgress,
                        setIsDownloadFinished
                    )
                }
            }
        })
    }
}

data class PodcastCategoryViewState(
    var selectedCategory: LowLevelCategory? = null,
    val categoryIndex: Int = 0,
    var contents: List<Content> = emptyList(),
)
