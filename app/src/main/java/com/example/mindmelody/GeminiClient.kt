package com.example.mindmelody

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenAIClient {
        private const val BASE_URL = "https://generativelanguage.googleapis.com/"
        private const val API_KEY = "AIzaSyD4d1sNBenKJScKTo1Af8R0Kpm6gk-VW0c"

        val api: OpenAIApiService by lazy {
                val client = OkHttpClient.Builder().addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                                .url(chain.request().url.newBuilder().addQueryParameter("key", API_KEY).build())
                                .build()
                        chain.proceed(newRequest)
                }.build()

                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                retrofit.create(GeminiApiService::class.java)
        }
}