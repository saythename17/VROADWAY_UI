package com.alphacircle.vroadway.data

import com.google.gson.annotations.SerializedName

data class BoardResponse(
    @SerializedName("items") val boardList: List<Board>
)

data class Board(
    var title: String = "",
    var description: String = "",
    var sorting: Int = 0
)