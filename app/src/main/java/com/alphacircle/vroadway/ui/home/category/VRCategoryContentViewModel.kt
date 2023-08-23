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

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.Graph
import com.alphacircle.vroadway.data.CategoryStore
import com.alphacircle.vroadway.data.EpisodeToPodcast
import com.alphacircle.vroadway.data.PodcastStore
import com.alphacircle.vroadway.data.PodcastWithExtraInfo
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.HighLevelCategory
import com.alphacircle.vroadway.data.category.LowLevelCategory
import com.alphacircle.vroadway.ui.home.discover.HighLevelCategoryViewState
import com.alphacircle.vroadway.util.NetworkModule
import com.alphacircle.vroadway.util.VroadwayAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
            NetworkModule.getContents(categoryId,
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
        NetworkModule.getContents(categoryId.toLong(), onSuccess = { it ->
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
}

data class PodcastCategoryViewState(
    val categoryIndex: Int = 0,
    var contents: List<Content> = emptyList()
)
