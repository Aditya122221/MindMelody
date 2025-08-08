package com.example.mindmelody

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VideoPlayer : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_video_player)
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        v.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                        )
                        insets
                }

                val webView = findViewById<WebView>(R.id.webView)
                val videoIds = intent.getStringArrayListExtra("videosId") ?: arrayListOf()
                if (videoIds.isEmpty()) {
                        finish()
                        return
                }
                val songId = intent.getStringExtra("songId")
                val joinedIds = videoIds.joinToString(",")
                val embedUrl = "https://www.youtube.com/embed/${songId}?playlist=$joinedIds&autoplay=1"

                webView.settings.javaScriptEnabled = true
                webView.settings.mediaPlaybackRequiresUserGesture = false
                webView.settings.domStorageEnabled = true
                webView.settings.loadWithOverviewMode = true
                webView.settings.useWideViewPort = true
                webView.webChromeClient = WebChromeClient()
                webView.loadUrl(embedUrl)
        }
}