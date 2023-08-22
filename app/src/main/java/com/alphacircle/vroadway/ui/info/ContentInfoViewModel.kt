package com.alphacircle.vroadway.ui.info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.HighLevelCategory
import com.alphacircle.vroadway.util.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration

data class ContentInfoUiState(
    val title: String = "",
    val categoryName: String = "",
    val description: String = "",
)

class ContentInfoViewModel(
    categoryId: Long,
    index: Int,
) : ViewModel() {
    private val _state = MutableStateFlow(ContentInfoViewState())

    val state: StateFlow<ContentInfoViewState>
        get() = _state

    init {
        viewModelScope.launch {
            NetworkModule.getContents(categoryId,
                onSuccess = {
                Log.println(Log.DEBUG, "VRCategory", it.toString())
                if(it.isNotEmpty()) state.value.content = it[index]
            })
        }
    }

    fun onCategorySelected(category: HighLevelCategory) {
//        _state.update { ContentInfoViewState(_state.value.content, category) }
    }
}

data class ContentInfoViewState(
    var content: Content? = null
)