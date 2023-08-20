package com.alphacircle.vroadway.ui.home.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.category.HighLevelCategory
import com.alphacircle.vroadway.util.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverViewModel : ViewModel() {
    private val _state = MutableStateFlow(HighLevelCategoryViewState())

    val state: StateFlow<HighLevelCategoryViewState>
        get() = _state

    init {
        viewModelScope.launch {
            NetworkModule.getAllCategories {
                if(it.isNotEmpty() && state.value.selectedCategory == null) {
                    state.value.selectedCategory = it[0]
                    state.value.categories = it
                }
            }
        }
    }

    fun onCategorySelected(category: HighLevelCategory) {
        _state.update { HighLevelCategoryViewState(_state.value.categories, category) }
    }
}

data class HighLevelCategoryViewState(
    var categories: List<HighLevelCategory> = emptyList(),
    var selectedCategory: HighLevelCategory? = null
)