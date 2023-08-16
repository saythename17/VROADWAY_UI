package com.alphacircle.vroadway.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alphacircle.vroadway.data.category.CategoryResponse
import com.alphacircle.vroadway.data.category.Depth1Category
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun create(): VroadwayAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VroadwayAPI::class.java)
    }

    fun getJSONData(list: MutableList<String>, context: Context) {
        val call: Call<CategoryResponse> = create().getCategories()

        call!!.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if(response.isSuccessful()) { // <--> response.code == 200
                    // 성공 처리
                    Log.println(Log.DEBUG, "NetworkModule", response.body().toString())

                    //ex)
                    Toast.makeText(context, "${response.body().toString()}", Toast.LENGTH_SHORT).show()
                } else { // code == 400
                    // 실패 처리
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.println(Log.DEBUG, "NetworkModule", t.message.toString())
            }
        })
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
}