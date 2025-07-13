package com.example.myapplication.data.retofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    private const val BASE_URL = "https://reqres.in/"
    val apiService: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}
