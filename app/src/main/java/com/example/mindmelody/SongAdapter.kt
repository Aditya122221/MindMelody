package com.example.mindmelody

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val songs: MutableList<Song>, private val listener: OnSongClickListener) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
        interface OnSongClickListener {
                fun onSongClick(position: Int)
                fun getCurrentPlayingPosition(): Int
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_song, parent, false)
                return SongViewHolder(view)
        }

        override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
                val song = songs[position]
                holder.bind(song, position == listener.getCurrentPlayingPosition())

                holder.itemView.setOnClickListener {
                        listener.onSongClick(position)
                }
        }

        override fun getItemCount(): Int = songs.size

        class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                private val titleTextView: TextView = itemView.findViewById(R.id.tvSongTitle)
                private val artistTextView: TextView = itemView.findViewById(R.id.tvSongArtist)
                private val durationTextView: TextView = itemView.findViewById(R.id.tvSongDuration)
                private val playIcon: ImageView = itemView.findViewById(R.id.ivPlayIcon)
                val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)

                fun bind(song: Song, isPlaying: Boolean) {
                        titleTextView.text = song.title
                        artistTextView.text = song.artist
                        durationTextView.text = formatDuration(song.duration)

                        if (isPlaying) {
                                playIcon.setImageResource(R.drawable.ic_pause)
                                playIcon.visibility = View.VISIBLE
                                itemView.setBackgroundResource(R.drawable.item_selected_background)
                        } else {
                                playIcon.setImageResource(R.drawable.ic_play_icon)
                                playIcon.visibility = View.VISIBLE
                                itemView.setBackgroundResource(R.drawable.item_default_background)
                        }
                }

                private fun formatDuration(seconds: Int): String {
                        val minutes = seconds / 60
                        val remainingSeconds = seconds % 60
                        return String.format("%d:%02d", minutes, remainingSeconds)
                }
        }
}