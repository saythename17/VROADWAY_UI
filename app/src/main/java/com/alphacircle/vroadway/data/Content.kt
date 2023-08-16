import com.google.gson.annotations.SerializedName

data class ContentResponse(
    val contentList: List<Content>
)

data class Content(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var accessControl: Boolean = false,
    var sorting: Int = 0,
    var runningTime: Int = 0,
    var categoryId: Int = 0,
    @SerializedName("banner") var bannerUrl: String? = null
)