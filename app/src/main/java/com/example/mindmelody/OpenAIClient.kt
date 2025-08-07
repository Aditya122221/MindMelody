package com.example.mindmelody

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenAIClient {
        private const val BASE_URL = "https://api.openai.com/v1/"
        private const val API_KEY = "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"

        val api: OpenAIApiService by lazy {
                val client = OkHttpClient.Builder().addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", API_KEY)
                                .build()
                        chain.proceed(newRequest)
                }.build()

                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                retrofit.create(OpenAIApiService::class.java)
        }
}