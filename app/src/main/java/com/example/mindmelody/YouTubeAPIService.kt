package com.example.mindmelody

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface YouTubeAPIService {
        @GET("search")
        fun searchVideos(
                @Query("part") part: String = "snippet",
                @Query("maxResults") maxResults: Int = 10,
                @Query("q") query: String,
                @Query("type") type: String = "video",
                @Query("key") apiKey: String
        ): Call<YouTubeResponse>
}