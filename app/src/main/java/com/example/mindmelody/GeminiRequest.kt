package com.example.mindmelody

data class GeminiRequest(val contents: List<GeminiContent>)

data class GeminiContent(
        val parts: List<GeminiPart>,
        val role: String = "user"
)

data class GeminiPart(
        val text: String
)