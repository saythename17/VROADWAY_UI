package com.alphacircle.vroadway.data

data class BoardResponse(
    val boardList: List<Board>
)

data class Board(
    var title: String = "",
    var description: String = "",
    var sorting: Int = 0
)