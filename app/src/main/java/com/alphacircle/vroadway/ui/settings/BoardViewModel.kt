package com.alphacircle.vroadway.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.Board
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.HighLevelCategory
import com.alphacircle.vroadway.util.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration

class BoardViewModel(
    boardType: String,
) : ViewModel() {
    private val _state = MutableStateFlow(BoardViewState())

    val state: StateFlow<BoardViewState>
        get() = _state

    init {
        viewModelScope.launch {
            NetworkModule.getBoards(boardType,
                onSuccess = {
                Log.println(Log.DEBUG, "BoardViewModel", it.toString())
                if(it.isNotEmpty()) state.value.boardList = it
            })
        }
    }
}

data class BoardViewState(
    var boardList: List<Board> = emptyList(),
)