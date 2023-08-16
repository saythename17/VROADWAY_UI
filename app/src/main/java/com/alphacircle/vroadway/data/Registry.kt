package com.alphacircle.vroadway.data

data class RegistryResponse(
    val RegistryList: List<Registry>
)

data class Registry(
    val categoryName: String = "",
    val serial:String = ""
)