package com.alphacircle.vroadway.data.category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("items") val categoryList: List<HighLevelCategory>
)

data class HighLevelCategory(
    var id: Long = 0,
    var parentId: Int = 0,
    var name: String = "",
    var accessType: String? = null,
    var sorting: Int = 0,
    var level: Int = 0,
    @SerializedName("children") var lowLevelCategoryList: List<LowLevelCategory> = listOf()
)