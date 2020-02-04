package com.example.itunessearch.data

/*
    Data class contains models songs JSON response from api iTunes
    request https://itunes.apple.com/lookup?id={collectionId}&entity=song
 */
data class SongsData (
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
    val contentAdvisoryRating: String,
    val trackCount: Int,
    val copyright: String,
    val country: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val kind: String,
    val trackId: Int,
    val trackName: String,
    val trackViewUrl: String,
    val previewUrl: String,
    val artworkUrl30: String,
    val trackPrice: Double,
    val trackExplicitness: String,
    val discCount: Int,
    val discNumber: Int,
    val trackNumber: Int,
    val trackTimeMillis: Long,
    val isStreamable: Boolean

)
