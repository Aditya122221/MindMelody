package com.example.mindmelody

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiClient {
        private const val BASE_URL = "https://generativelanguage.googleapis.com/"
        const val API_KEY = "AIzaSyD4d1sNBenKJScKTo1Af8R0Kpm6gk-VW0c"

        val api: GeminiApiService by lazy {
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                retrofit.create(GeminiApiService::class.java)
        }
}