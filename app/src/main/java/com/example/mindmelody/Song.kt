package com.example.mindmelody

data class Song(
        val id: String,
        val title: String,
        val thumbnail: String,
        val artist: String,
        val duration: Int = 0,
)