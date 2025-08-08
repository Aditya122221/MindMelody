package com.example.mindmelody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.media.MediaPlayer
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmelody.databinding.FragmentTextBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextFragment : Fragment(), SongAdapter.OnSongClickListener{

        private lateinit var binding: FragmentTextBinding
        private lateinit var recyclerView: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var emptyTextView: TextView
        private lateinit var songAdapter: SongAdapter
        private var mediaPlayer: MediaPlayer? = null
        private var currentPlayingPosition = -1
        private val songs = mutableListOf<Song>()
        val model = GenerativeModel(modelName = "gemini-2.5-pro", apiKey = "AIzaSyCEs6dbHcRmLtvafwVkx7jD9_90RoqYgPY")

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                binding = FragmentTextBinding.inflate(inflater, container, false)
                return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                recyclerView = binding.recyclerView
                emptyTextView = binding.emptyTextView
                progressBar = binding.progressBar

                songAdapter = SongAdapter(songs, this)
                recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = songAdapter
                }

                binding.btnGetSuggestion.setOnClickListener {
                        val sug = binding.etMoodInput.text.toString().trim()

                        if(sug.isNotEmpty()) {
                                showLoading(true)
                                var keyword = "party song"
                                val userPrompt = "Extract one mood-related keyword (like party, chill, gym, etc.) from this sentence: \"$sug\". Just give the keyword only."

                                lifecycleScope.launch {
                                        try {
                                                val response = model.generateContent(userPrompt)
                                                keyword = response.text.toString()
                                                val cleanKeyword = keyword.replace("\"", "").trim()
                                                val call = RetrofitClient.youtubeService.searchVideos(
                                                        query = "$cleanKeyword songs",
                                                        apiKey = "AIzaSyCEs6dbHcRmLtvafwVkx7jD9_90RoqYgPY"
                                                )

                                                call.enqueue(object : retrofit2.Callback<YouTubeResponse> {
                                                        override fun onResponse(call: Call<YouTubeResponse>, response: retrofit2.Response<YouTubeResponse>) {
                                                                if (response.isSuccessful) {
                                                                        val videos = response.body()?.items ?: emptyList()
                                                                        songs.clear()
                                                                        for(video in videos) {
                                                                                val videoId = video.id.videoId ?: continue
                                                                                val title = video.snippet.title
                                                                                val thumbnail = video.snippet.thumbnails.medium.url
                                                                                val artist = video.snippet.channelTitle

                                                                                songs.add(Song(videoId, title, thumbnail, artist))
                                                                        }
                                                                        songAdapter.notifyDataSetChanged()
                                                                        showLoading(false)
                                                                } else {
                                                                        showLoading(false)
                                                                        binding.showError.text = "Error: ${response.message()}"
                                                                }
                                                        }

                                                        override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                                                                showLoading(false)
                                                                binding.showError.text = "Failure: ${t.message}"
                                                        }
                                                })
                                        } catch (e: Exception) {
                                                showLoading(false)
                                                binding.showError.text = "Gemini error: ${e.message}"
                                        }
                                }
                        } else {
                                Toast.makeText(requireContext(), "Empty field", Toast.LENGTH_LONG).show()
                        }
                }
        }

        private fun showLoading(isLoading: Boolean) {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        override fun onSongClick(position: Int) {
                val song = songs[position]

                // Stop current playing song if any
                stopCurrentSong()
        }

        private fun stopCurrentSong() {
                mediaPlayer?.apply {
                        if (isPlaying) {
                                stop()
                        }
                        release()
                }
                mediaPlayer = null

                val previousPosition = currentPlayingPosition
                currentPlayingPosition = -1
                if (previousPosition != -1) {
                        songAdapter.notifyItemChanged(previousPosition)
                }
        }

        private fun updatePlayingState(position: Int) {
                val previousPosition = currentPlayingPosition
                currentPlayingPosition = position

                // Notify adapter to update the UI
                if (previousPosition != -1) {
                        songAdapter.notifyItemChanged(previousPosition)
                }
                songAdapter.notifyItemChanged(currentPlayingPosition)
        }

        override fun getCurrentPlayingPosition(): Int = currentPlayingPosition
        private fun updateEmptyState() {
                emptyTextView.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
                recyclerView.visibility = if (songs.isEmpty()) View.GONE else View.VISIBLE
        }

        override fun onPause() {
                super.onPause()
                mediaPlayer?.pause()
        }

        override fun onResume() {
                super.onResume()
                mediaPlayer?.start()
        }

        override fun onDestroyView() {
                super.onDestroyView()
                stopCurrentSong()
        }
}