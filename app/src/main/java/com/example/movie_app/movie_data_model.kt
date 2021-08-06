package com.example.movie_app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


//data class movie_model(
//    val title: String,
//    val page: Int,
//    val results: List<Result>,
//    val total_pages: Int,
//    val total_results: Int
//)

data class movie_model (
    val id: Int,
    val popularity: Double,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val imdb_id: String,
    val videoKey: String,
)
