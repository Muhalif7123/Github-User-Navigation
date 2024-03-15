package com.belajar.githubusernavigation.data.retrofit

import com.belajar.githubusernavigation.data.response.DataDetail
import com.belajar.githubusernavigation.data.response.ItemsItem
import com.belajar.githubusernavigation.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(@Query("q")q: String, @Query("per_page")perPage: Int) : Call<UserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(@Path("username")login: String): Call<DataDetail>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username")login: String, @Query("per_page")perPage: Int): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowings(@Path("username")login: String, @Query("per_page")perPage: Int): Call<List<ItemsItem>>
}