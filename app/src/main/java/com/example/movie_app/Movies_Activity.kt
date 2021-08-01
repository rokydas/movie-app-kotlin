package com.example.movie_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.movies_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Movies_Activity: AppCompatActivity() {

    var BASE_URL = "https://api.themoviedb.org/3/"
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        movies_recycler.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        movies_recycler.layoutManager = gridLayoutManager

        getMovies()
    }

    private fun getMovies() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TopRatedInterface::class.java)

        val retrofitData = retrofitBuilder.getMovies()
        retrofitData.enqueue(object : Callback<List<movie_model>> {
            override fun onResponse(call: Call<List<movie_model>>, response: Response<List<movie_model>>) {
                val responseBody = response.body() !!
                Log.d("response", response.toString())

                val movieAdapter = MovieAdapter(baseContext, responseBody)
                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter
            }

            override fun onFailure(call: Call<List<movie_model>>, t: Throwable) {
                Log.d("MainActivity", "roky: " + call)
            }
        })
    }
}