package com.example.mindmelody

import android.util.Log
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
//                                val request = GeminiRequest(
//                                        contents = listOf(
//                                                GeminiContent(
//                                                        parts = listOf(GeminiPart(userPrompt))
//                                                )
//                                        )
//                                )

                                lifecycleScope.launch {
                                        try {
                                                val response = model.generateContent(userPrompt)
                                                keyword = response.text.toString()
                                                val cleanKeyword = keyword.replace("\"", "").trim()
                                                RetrofitClient.instance.searchTracks(cleanKeyword, 25)
                                                        .enqueue(object : Callback<DeezerResponse> {
                                                                override fun onResponse(
                                                                        call: Call<DeezerResponse>,
                                                                        response: Response<DeezerResponse>
                                                                ) {
                                                                        showLoading(false)
                                                                        if(response.isSuccessful && response.body()?.data != null) {
                                                                                val data = response.body()!!.data
                                                                                if (data.isEmpty()) {
                                                                                        binding.showError.text = "No songs found for keyword: $keyword"
                                                                                        return
                                                                                }
                                                                                binding.showError.text = keyword
                                                                                val tracks = response.body()?.data?.map { track ->
                                                                                        Song(
                                                                                                title = track.title,
                                                                                                artist = track.artist.name,
                                                                                                audioUrl = track.preview,
                                                                                                duration = 300,
                                                                                                albumArt = track.album
                                                                                        )
                                                                                } ?: emptyList()

                                                                                songs.clear()
                                                                                songs.addAll(tracks)
                                                                                songAdapter.notifyDataSetChanged()
                                                                                showLoading(false)
                                                                                updateEmptyState()
                                                                        } else {
                                                                                showLoading(false)
                                                                                binding.showError.text = "GPT error: ${response.code()}"
                                                                        }
                                                                }

                                                                override fun onFailure(call: Call<DeezerResponse>, t: Throwable) {
                                                                        showLoading(false)
                                                                        binding.showError.text = "Deezer server not running"
                                                                        Toast.makeText(requireContext(), "Server not running", Toast.LENGTH_LONG).show()
                                                                }
                                                        })
                                        } catch (e: Exception) {
                                                showLoading(false)
                                                binding.showError.text = "Gemini error: ${e.message}"
                                        }
                                }

//                                GeminiClient.api.generateContent(GeminiClient.API_KEY, request).enqueue(object : Callback<GeminiResponse> {
//                                        override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
//                                                if (response.isSuccessful) {
//                                                        val keyword = response.body()
//                                                                ?.candidates?.firstOrNull()
//                                                                ?.content?.parts?.firstOrNull()
//                                                                ?.text?.lowercase()?.trim()
//                                                        binding.showError.text = "I am inside gpt code"
//                                                        keyword?.let {
//                                                                binding.showError.text = "I have reached inside keyword $keyword"
//                                                                RetrofitClient.instance.searchTracks(keyword)
//                                                                        .enqueue(object : Callback<DeezerResponse> {
//                                                                                override fun onResponse(
//                                                                                        call: Call<DeezerResponse>,
//                                                                                        response: Response<DeezerResponse>
//                                                                                ) {
//                                                                                        showLoading(false)
//                                                                                        if(response.isSuccessful) {
//                                                                                                val tracks = response.body()?.data
//                                                                                                binding.showError.text = "I got the track"
//                                                                                        } else {
//                                                                                                showLoading(false)
//                                                                                                binding.showError.text = "GPT error: ${response.code()}"
//                                                                                        }
//                                                                                }
//
//                                                                                override fun onFailure(call: Call<DeezerResponse>, t: Throwable) {
//                                                                                        showLoading(false)
//                                                                                        binding.showError.text = "Deezer server not running"
//                                                                                        Toast.makeText(requireContext(), "Server not running", Toast.LENGTH_LONG).show()
//                                                                                }
//                                                                        })
//                                                        }
//                                                } else {
//                                                        showLoading(false)
//                                                        binding.showError.text = "Gemini error: ${response.code()}}"
//                                                }
//                                        }
//
//                                        override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
//                                                showLoading(false)
//                                                binding.showError.text = "gpt server not running"
//                                                Toast.makeText(requireContext(), "Server not running", Toast.LENGTH_LONG).show()
//                                        }
//                                })
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

                // Start new song
                try {
                        // For streaming URLs, use setDataSource instead of MediaPlayer.create
                        mediaPlayer = MediaPlayer().apply {
                                setDataSource(song.audioUrl)
                                prepareAsync() // Use async preparation for streaming

                                setOnPreparedListener { mp ->
                                        mp.start()
                                        updatePlayingState(position)
                                }

                                setOnCompletionListener {
                                        stopCurrentSong()
                                }

                                setOnErrorListener { _, what, extra ->
                                        Toast.makeText(requireContext(), "Error playing song: $what, $extra", Toast.LENGTH_LONG).show()
                                        stopCurrentSong()
                                        true
                                }
                        }

                } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Failed to play song: ${e.message}", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                }
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

        fun addSong(song: Song) {
                songs.add(song)
                songAdapter.notifyItemInserted(songs.size - 1)
                updateEmptyState()
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