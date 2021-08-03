package com.example.movie_app
import retrofit2.Call
import retrofit2.http.GET

interface TopRatedInterface {
    @GET("movies")
    fun getMovies() : Call<List<movie_model>>
}
