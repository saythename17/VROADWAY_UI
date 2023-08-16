package com.alphacircle.vroadway.util

import ContentResponse
import com.alphacircle.vroadway.data.BoardResponse
import com.alphacircle.vroadway.data.RegistryResponse
import com.alphacircle.vroadway.data.User
import com.alphacircle.vroadway.data.category.CategoryResponse
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VroadwayAPI {
    /**
     * Board : 특정 보드 타입에 대한 모든 보드 목록, 개인정보처리방침 조회
     **/
    @GET("/api/boards/notice")
    fun getNotice(
        @Query("language") language: String = "ko-KR"
    ): Call<List<BoardResponse>>

    @GET("/api/boards/faq")
    fun getFAQ(
        @Query("language") language: String = "ko-KR"
    ): Call<List<BoardResponse>>

    @GET("/api/boards/policy")
    fun getPolicy(
        @Query("language") language: String = "ko-KR"
    ): Call<List<BoardResponse>>

    @GET("/api/boards/policies/privacy")
    fun getPrivacy(): Call<List<BoardResponse>>

    /**
     * Category : 모든 카테고리 목록 조회
     **/
    @GET("/api/categories")
    fun getCategories(): Call<CategoryResponse>

    /**
     * Content : 특정 카테고리에 대한 모든 컨텐츠 목록 조회
     **/
    @GET("/api/contents/categories/id={id}")
    fun getContent(
        @Path("id") id: Int
    ): Call<ContentResponse>

    /**
     * Registry : 특정 유저의 모든 시리얼 등록 정보 조회/시리얼 등록(IAP)
     **/
    @GET("/api/registries")
    fun getRegistry(
//        @Header("Authorization") accessToken: String
    ): Call<RegistryResponse>

    @POST("/api/registries/iap")
    fun postRegistryIAP(
//        @Header("Authorization") accessToken: String
        @Query("categoryId") categoryId: Int = 0
    ): Call<Response>

    /**
     * Auth: 로그인(회원가입) 처리
     **/
    @POST("/api/auth/login/google")
    fun postLogin(): Call<String> //TODO accessToken 저장

    /**
     * User: 회원 정보 확인, 회원 탈퇴, 리뷰 등록
     **/
    @DELETE("/api/users")
    fun deleteUser(): Call<Response>

    @GET("/api/users")
    fun getUserInfo(): Call<User>

    @POST("/api/reviews")
    fun postReview(
        @Query("comment") comment: String = ""
    ): Call<Response>
}