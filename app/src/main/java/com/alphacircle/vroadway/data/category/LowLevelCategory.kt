package com.alphacircle.vroadway.data.category

data class LowLevelCategory (
    var id: Int = 0,
    var parentId: Int = 0,
    var name: String = "",
    private var accessType: String = "",
    var sorting: Int = 0,
    var level: Int = 0,
    var imageUrl: String = "https://i.namu.wiki/i/1RW2t0TZ1opyU_m5F9FKmvF01nkcK17vdRUgXXvFOK-HDfYU8j8vYWB5xgDRcf-kURqOR92yTdKLnESLDIWADHXfDKBP37V5rVEU_Nq1F4TmNxPXDmlPab6kISdTWJArKu8vd6u5DPCvFcbUayMxEQ.webp"
)