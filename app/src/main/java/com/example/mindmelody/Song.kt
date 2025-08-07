package com.example.mindmelody

class Song(
        val title: String,
        val artist: String,
        val audioUrl: String,
        val duration: Int = 0, // in seconds
        val albumArt: Album
)