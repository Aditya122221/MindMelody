package com.example.mindmelody

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
        private val BASE_URL = "https://api.deezer.com/"

        val instance: DeezerApiService by lazy {
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                retrofit.create(DeezerApiService::class.java)
        }
}