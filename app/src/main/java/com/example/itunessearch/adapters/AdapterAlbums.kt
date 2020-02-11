package com.example.itunessearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itunessearch.R
import com.example.itunessearch.data.AlbumsData

class AdapterAlbums(private var album: List<AlbumsData>): IAdapterAlbums(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(album[position].collectionName, album[position].artistName, album[position].artworkUrl60)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private lateinit var textViewAlbumName: TextView
        private lateinit var textViewActorName: TextView
        private lateinit var imageViewArtwork: ImageView
        fun bindItems( collectionName: String, artistName: String, artworkUrl : String) {
            textViewAlbumName= itemView.findViewById(R.id.textViewTitle)
            textViewAlbumName.text = collectionName
            textViewActorName= itemView.findViewById(R.id.textViewActors)
            textViewActorName.text = artistName
            imageViewArtwork= itemView.findViewById(R.id.imageViewArtwork)
            Glide.with(itemView).load(artworkUrl).into(imageViewArtwork)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    fun pagingAdd(albumsData: List<AlbumsData>){
        val oldSize = this.album.size
        this.album = albumsData
        val sizeNew = this.album.size
        notifyItemRangeChanged(oldSize, sizeNew)
    }

    fun update(albumsData: List<AlbumsData>){
        album = albumsData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = album.size
}