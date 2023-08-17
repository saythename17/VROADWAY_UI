package com.alphacircle.vroadway.data.category
import com.alphacircle.vroadway.data.Board
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("items") val categoryList: List<Depth1Category>
)

data class Depth1Category(
    var id: Long = 0,
    var parentId: Int = 0,
    var name: String = "",
    var accessType: String = "",
    var sorting: Int = 0,
    var level: Int = 0,
    @SerializedName("children") var depth2CategoryList: List<Depth2Category> = listOf()
)