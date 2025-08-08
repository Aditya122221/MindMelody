package com.example.mindmelody

data class YouTubeResponse(val items: List<VideoItem>)

data class VideoItem(
        val id: VideoId,
        val snippet: Snippet
)

data class VideoId(
        val videoId: String
)

data class Snippet(
        val title: String,
        val thumbnails: Thumbnails,
        val channelTitle: String
)

data class Thumbnails(
        val medium: ThumbnailInfo
)

data class ThumbnailInfo(
        val url: String
)