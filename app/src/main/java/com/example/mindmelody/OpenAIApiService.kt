package com.example.mindmelody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApiService {
        @Headers("Content-Type: application/json")
        @POST("chat/completions")
        fun getChatResponse(@Body request: GPTRequest): Call<GPTResponse>
}