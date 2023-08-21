package com.alphacircle.vroadway.data.category

import com.google.gson.annotations.SerializedName

data class ContentResponse(
    @SerializedName("items") val contentList: List<Content>
)

data class Content(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var accessControl: Boolean = false,
    var sorting: Int = 0,
    var runningTime: Long = 0,
    var categoryId: Int = 0,
    @SerializedName("banner") var bannerUrl: String? = null
)