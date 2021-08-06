package com.example.movie_app

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.movies_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Movies_Activity: AppCompatActivity(), MovieAdapter.myOnClickListener {
    var BASE_URL = "https://api.themoviedb.org/3/"
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        movies_recycler.setVisibility(View.GONE);

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
        retrofitData.enqueue(object : Callback<movie_model> {
            override fun onResponse(call: Call<movie_model>, response: Response<movie_model>) {
                spin_kit.setVisibility(View.GONE);
                movies_recycler.setVisibility(View.VISIBLE);

                Toast.makeText(this@Movies_Activity, "success", Toast.LENGTH_SHORT).show()

                val responseBody = response.body() !!
                var moviesData = listOf<Result>()
                moviesData = responseBody.results

                val movieAdapter = MovieAdapter(baseContext, moviesData, this@Movies_Activity)

                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter

            }

            override fun onFailure(call: Call<movie_model>, t: Throwable) {
                Toast.makeText(this@Movies_Activity, t.message, Toast.LENGTH_LONG).show()
                val builder = AlertDialog.Builder(this@Movies_Activity)

                builder.setTitle("Network issue")
                builder.setMessage("Your network is turned off. Please turn on to load movies...")
                builder.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int -> finish() })
                builder.show()
            }


        })
    }

    override fun onClick(position: Int) {
//        val intent = Intent(this, Movie_Details_Activity::class.java)
//        intent.putExtra("movie", moviesData[position])
//        startActivity(intent)
    }


}

