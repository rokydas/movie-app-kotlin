package com.example.movie_app

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
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
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.PorterDuff




class Movies_Activity: AppCompatActivity(), MovieAdapter.myOnClickListener {
    var BASE_URL = "https://movie-app10.herokuapp.com/"
    lateinit var gridLayoutManager: GridLayoutManager
    var moviesData = listOf<movie_model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        movies_recycler.setVisibility(View.GONE);

//        https://www.youtube.com/watch?v=2WUhVb5jj8I

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
                Toast.makeText(this@Movies_Activity, "success", Toast.LENGTH_LONG).show()
                progressBar.setVisibility(View.GONE);
                movies_recycler.setVisibility(View.VISIBLE);
                Toast.makeText(this@Movies_Activity, "success", Toast.LENGTH_SHORT).show()
                val responseBody = response.body() !!
                moviesData = responseBody

                val movieAdapter = MovieAdapter(baseContext, responseBody, this@Movies_Activity)

                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter

            }

            override fun onFailure(call: Call<List<movie_model>>, t: Throwable) {
                Toast.makeText(this@Movies_Activity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(position: Int) {

        val intent = Intent(this, Movie_Details_Activity::class.java)
        intent.putExtra("movie", moviesData[position])
        startActivity(intent)

    }



}

