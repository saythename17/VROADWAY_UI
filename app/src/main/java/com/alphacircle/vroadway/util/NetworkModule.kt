package com.alphacircle.vroadway.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.alphacircle.vroadway.data.Category
import com.alphacircle.vroadway.data.category.CategoryResponse
import com.alphacircle.vroadway.data.category.Depth1Category
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://211.32.50.73:10322"
    private const val TIMEOUT_LIMIT = 5000L

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    fun createAPI(): VroadwayAPI {
        return provideRetrofit()
            .create(VroadwayAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorizedRetrofit(token: String): Retrofit {
        // Initialize Network Interceptor
        val networkInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(newRequest)

            response.newBuilder().build()
        }

        val client = OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    fun createAuthorizedAPI(token: String): VroadwayAPI {
        return provideAuthorizedRetrofit(token)
            .create(VroadwayAPI::class.java)
    }

//    @Singleton
//    fun getAllCategories(list: MutableList<Category>, context: Context) {
//        val call: Call<CategoryResponse> = createAPI().getCategories()
//
//        call!!.enqueue(object : Callback<CategoryResponse> {
//            override fun onResponse(
//                call: Call<CategoryResponse>,
//                response: Response<CategoryResponse>
//            ) {
//                if (response.isSuccessful) { // <--> response.code == 200
//                    if (response.body()?.categoryList == null) throw Throwable("get categories provides null") //TODO XIO change safe code before release
//                    // 성공 처리
//                    Log.println(Log.DEBUG, "NetworkModule", response.body().toString())
//                    Toast.makeText(
//                        context,
//                        "${response.body().toString().length}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    var responseList: CategoryResponse = response.body()!!
//
//                    for (i in responseList.categoryList) {
//                        val category = Category(name = i.name, id = i.id)
//                        list.add(category)
//                    }
//                } else { // code == 400
//                    // 실패 처리
//                    Log.println(Log.DEBUG, "NetworkModule", response.body().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
//                Log.println(Log.DEBUG, "NetworkModule", t.message.toString())
//            }
//        })
//    }

    fun getAllCategories2(categories: MutableList<Depth1Category>, context: Context): Flow<List<Depth1Category>> {
        val call: Call<CategoryResponse> = createAPI().getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) { // <--> response.code == 200
                    if (response.body()?.categoryList == null) {
//                        throw Throwable("get categories provides null") //TODO XIO change safe code before release
                        Log.println(Log.DEBUG, "NetworkModule", "categories provides null")
                    }
                    // 성공 처리
                    Log.println(Log.DEBUG, "NetworkModule", response.body().toString())
                    Toast.makeText(
                        context,
                        "${response.body().toString().length}",
                        Toast.LENGTH_SHORT
                    ).show()

                    var responseList: CategoryResponse = response.body()!!

                    val newCategories = responseList.categoryList.map { i ->
                        Depth1Category(
                            id = i.id,
                            parentId = i.parentId,
                            name = i.name,
                            accessType = i.accessType,
                            sorting = i.sorting,
                            level = i.level,
                            depth2CategoryList = i.depth2CategoryList
                        )
                    }

                    categories.clear()
                    categories.addAll(newCategories)

//                    if (categories.isNotEmpty()) {
//                        selected.value = categories[0]
//                    }
                } else { // code == 400
                    // 실패 처리
                    Log.println(Log.DEBUG, "NetworkModule", response.body().toString())
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.println(Log.DEBUG, "NetworkModule", t.message.toString())
            }
        })

        return flow { categories.map { it } }
    }
}