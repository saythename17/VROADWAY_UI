package com.alphacircle.vroadway.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphacircle.vroadway.data.Board
import com.alphacircle.vroadway.util.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BoardViewModel(
    boardType: String,
) : ViewModel() {
    private val _state = MutableStateFlow(BoardViewState())

    val state: StateFlow<BoardViewState>
        get() = _state

    init {
        viewModelScope.launch {
            RetrofitService.getBoards(boardType,
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