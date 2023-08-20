package com.alphacircle.vroadway.data.category

data class LowLevelCategory (
    var id: Int = 0,
    var parentId: Int = 0,
    var name: String = "",
    private var accessType: String = "",
    var sorting: Int = 0,
    var level: Int = 0,
)