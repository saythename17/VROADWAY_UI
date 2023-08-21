package com.alphacircle.vroadway.data.category

import com.google.gson.annotations.SerializedName

data class AssetResponse(
    @SerializedName("items") val assetList: List<Asset>
)

data class Asset(
    var id: Long = 0,
    var name: String = "",
    var contentType: String = "",
    var purpose: String = "",
    var size: Int = 0,
    var contentId: Int = 0,
    var location: String = "", //해당 파일을 다운 받을 수 있는 실제 cdn 주소 (https://...)
    var tag: String = ""
)