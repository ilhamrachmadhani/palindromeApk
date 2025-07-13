package com.example.myapplication.data.retofit

import com.example.myapplication.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface APIService {
    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 6
    ): Response <UserResponse>
}
