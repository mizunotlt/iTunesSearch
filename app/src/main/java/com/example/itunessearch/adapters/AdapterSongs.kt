package com.example.itunessearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearch.R
import com.example.itunessearch.data.SongsData
import java.lang.StringBuilder


class AdapterSongs(private var songs: ArrayList<SongsData>): IAdapterSongs(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(songs[position].trackName, songs[position].trackNumber)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private lateinit var textViewNameTrack: TextView
        fun bindItems( trackName: String, trackNumber: Int) {
            textViewNameTrack = itemView.findViewById(R.id.nameSongs)
            val sb = StringBuilder()
            sb.append(trackNumber)
            sb.append(") ")
            sb.append(trackName)
            textViewNameTrack.text = sb.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_songs_layout, parent, false)
        return ViewHolder(itemView)
    }

    fun update(songsData: ArrayList<SongsData>){
        songs = songsData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = songs.size
}