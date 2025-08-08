package com.example.mindmelody

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
        private val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        val youtubeService: YouTubeAPIService by lazy {
                Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(YouTubeAPIService::class.java)
        }
}