package com.example.movie_app
import com.example.movie_app.models.movieDetails
import com.example.movie_app.models.results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.http.Path


interface TopRatedInterface {
    @GET("movie/top_rated?api_key=f96ac62d92ada173838748fa0f087eef")
    fun getMovies() : Call<results>
}

//interface MovieDetailsInterface {
//    @GET("movie/{id}?api_key=f96ac62d92ada173838748fa0f087eef")
//    fun getDetails() : Call<movieDetails>
//}

//interface MovieDetailsInterface {
//    @GET("movie/{id}?api_key=f96ac62d92ada173838748fa0f087eef")
//    fun getDetails(@Path  id: String?): Call<movieDetails>
//}

//interface MovieDetailsInterface {
//    @GET("movie/{id}?api_key=f96ac62d92ada173838748fa0f087eef")
//    fun getDetails(@Path(value = "id", encoded = true) fullUrl: String?): Call<movieDetails?>?
//}

interface MovieDetailsInterface {
    @GET("movie/{id}?api_key=f96ac62d92ada173838748fa0f087eef")
    fun getDetails(@Path(value = "id", encoded = true) id:  String): Call<movieDetails>
}
