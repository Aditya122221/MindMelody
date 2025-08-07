package com.example.mindmelody

data class GPTResponse(val choices: List<Choice>)

data class Choice(
        val message: Message
)