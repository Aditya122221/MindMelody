package com.example.mindmelody

data class GPTResponse(val candidates: List<GeminiCandidate>)

data class GeminiCandidate(
        val content: GeminiContentResult
)

data class GeminiContentResult(
        val parts: List<GeminiPartResult>
)

data class GeminiPartResult(
        val text: String
)