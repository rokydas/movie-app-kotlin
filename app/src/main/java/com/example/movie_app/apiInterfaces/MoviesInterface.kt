package com.example.movie_app
import com.example.movie_app.models.results
import retrofit2.Call
import retrofit2.http.GET

interface TopRatedInterface {
    @GET("movie/top_rated?api_key=f96ac62d92ada173838748fa0f087eef")
    fun getMovies() : Call<results>
}
