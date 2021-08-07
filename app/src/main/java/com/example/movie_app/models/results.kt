package com.example.movie_app.models

data class results(
    val title: String,
    val page: Int,
    val results: List<movies>,
    val total_pages: Int,
    val total_results: Int
)

//@Parcelize
//data class movie_model (
//    val id: Int,
//    val popularity: Double,
//    val title: String,
//    val overview: String,
//    val poster_path: String,
//    val release_date: String,
//    val imdb_id: String,
//    val videoKey: String,
//): Parcelable
