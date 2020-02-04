package com.example.itunessearch.data

/*
    Data class contains models album JSON response from api iTunes
 */
data class AlbumsData (
    val wrapperType: String,
    val collectionType: String,
    val artistId: Int,
    val collectionId: Int,
    val amgArtistId: Int,
    val artistName: String,
    val collectionName: String,
    val collectionCensoredName: String,
    val artistViewUrl: String,
    val collectionViewUrl: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val collectionPrice: Double,
    val collectionExplicitness: String,
    val trackCount: Int,
    val copyright: String,
    val country: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String
)