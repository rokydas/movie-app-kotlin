package com.example.movie_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.movies_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Movies_Activity: AppCompatActivity() {

    var BASE_URL = "https://movie-app10.herokuapp.com/"
    lateinit var gridLayoutManager: GridLayoutManager
    var isLoaded = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        movies_recycler.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        movies_recycler.layoutManager = gridLayoutManager

        getMovies()

//        getMoviesFromDemo()
    }

//    private fun getMoviesFromDemo() {
//        val movieAdapter = MovieAdapter(this, demoData)
//        movieAdapter.notifyDataSetChanged()
//        movies_recycler.adapter = movieAdapter
//    }

    private fun getMovies() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TopRatedInterface::class.java)

        val retrofitData = retrofitBuilder.getMovies()
        retrofitData.enqueue(object : Callback<List<movie_model>> {
            override fun onResponse(call: Call<List<movie_model>>, response: Response<List<movie_model>>) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this@Movies_Activity, "success", Toast.LENGTH_SHORT).show()
                val responseBody = response.body() !!

                val movieAdapter = MovieAdapter(baseContext, responseBody)
                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter
            }

            override fun onFailure(call: Call<List<movie_model>>, t: Throwable) {
                Toast.makeText(this@Movies_Activity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}