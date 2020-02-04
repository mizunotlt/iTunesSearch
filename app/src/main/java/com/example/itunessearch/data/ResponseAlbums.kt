package com.example.itunessearch.data
/*
    Example response from ItunesApi
    {
        resultCount : number,
        results : [
            {},
            {} ]
    }
 */
data class ResponseAlbums (
    val resultCount: Int,
    val results: ArrayList<AlbumsData>
)
