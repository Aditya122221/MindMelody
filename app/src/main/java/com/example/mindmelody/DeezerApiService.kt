package com.example.mindmelody

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {
        @GET("search")
        fun searchTracks(@Query("q") mood: String, @Query("index") index : Int = 0): Call<DeezerResponse>
}