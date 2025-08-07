package com.example.mindmelody

data class DeezerResponse (val data: List<Track>)

data class Track(
        val title: String,
        val artist: Artist,
        val preview: String,
        val album: Album
)

data class Artist(
        val name: String
)

data class Album(
        val cover: String
)