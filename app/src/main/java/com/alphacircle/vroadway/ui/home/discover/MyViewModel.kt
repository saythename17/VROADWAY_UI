package com.alphacircle.vroadway.ui.home.discover

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.category.Depth1Category
import com.alphacircle.vroadway.util.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyViewModel(context: Context) : ViewModel() {
    val categories = mutableStateListOf<Depth1Category>()

    // Holds our currently selected category
    private val _selectedCategory = MutableStateFlow<Depth1Category?>(null)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(MyDiscoverViewState())

    val state: StateFlow<MyDiscoverViewState>
        get() = _state

    init {
        viewModelScope.launch {
            // Combines the latest value from each of the flows, allowing us to generate a
            // view state instance which only contains the latest values.
            combine(
                NetworkModule.getAllCategories2(categories, context)
                    .onEach { categories ->
                        Log.println(Log.DEBUG, "MyViewModel", categories.toString())
                        // If we haven't got a selected category yet, select the first
                        if (categories.isNotEmpty() && _selectedCategory.value == null) {
                            Log.println(Log.DEBUG, "MyViewModel", "init selected")
                            _selectedCategory.value = categories[0]
                        }
                    },
                _selectedCategory
            ) { categories, selectedCategory ->
                MyDiscoverViewState(
                    categories = categories,
                    selectedCategory = selectedCategory
                )
            }.collect { _state.value = it }
        }
    }

    fun onCategorySelected(category: Depth1Category) {
        _selectedCategory.value = category
        Log.println(Log.DEBUG, "MyViewModel", "_selectedCategory: " + _selectedCategory.value)
    }

}

data class MyDiscoverViewState(
    val categories: List<Depth1Category> = emptyList(),
    val selectedCategory: Depth1Category? = null
)