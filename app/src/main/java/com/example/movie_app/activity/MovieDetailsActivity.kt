package com.example.movie_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import coil.load
import com.example.movie_app.MovieDbHandler
import com.example.movie_app.MovieDetailsInterface
import com.example.movie_app.R
import com.example.movie_app.models.movieDetails
import com.example.movie_app.models.movies
import kotlinx.android.synthetic.main.movie_details_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.Activity

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_app.VideoInterface
import com.example.movie_app.adapter.MovieAdapter
import com.example.movie_app.adapter.VideoAdapter
import com.example.movie_app.models.videos
import kotlinx.android.synthetic.main.movie_details_activity.spin_kit
import kotlinx.android.synthetic.main.movies_activity.*


class MovieDetailsActivity : AppCompatActivity(), VideoAdapter.myOnClickListener {

    var BASE_URL = "https://api.themoviedb.org/3/"
    var responseBody = movieDetails(0, "", 0.0, "", "", "")
    val movieDbHandler: MovieDbHandler = MovieDbHandler(this)
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

        detailsContent.visibility = View.GONE

        videos_recycler.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 1)

        videos_recycler.layoutManager = gridLayoutManager

        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("id")

        val idWithStr: String = "id: " + id

        getDetailsOfMovie(idWithStr)
        getVideoOfMovie(idWithStr)

        star.setOnClickListener() {
            val status = movieDbHandler.addFavoriteMovie(movies(responseBody.id , responseBody.title, responseBody.poster_path))
            if(status > -1) {
                changeVisibilityOfStar(star, fillStar)
                Toast.makeText(this, "Added this movie to your favorite", Toast.LENGTH_LONG).show()
            }
        }

        fillStar.setOnClickListener() {
            val status = movieDbHandler.deleteEmployee(responseBody.id)
            if(status > -1) {
                changeVisibilityOfStar(fillStar, star)
                Toast.makeText(this, "Removed this movie from your favorite", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getVideoOfMovie(idWithStr: String) {
        val id = idWithStr.substring(4, idWithStr.length)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(VideoInterface::class.java)

        val retrofitData = retrofitBuilder.getVideos(id)

        retrofitData.enqueue(object : Callback<videos> {
            override fun onResponse(call: Call<videos>, response: Response<videos>) {
                val responseBody = response.body() !!
                val videoList = responseBody.results
                val videoAdapter = VideoAdapter(baseContext, videoList, this@MovieDetailsActivity)
                videoAdapter.notifyDataSetChanged()
                videos_recycler.adapter = videoAdapter
            }

            override fun onFailure(call: Call<videos>, t: Throwable) {
                Toast.makeText(this@MovieDetailsActivity, "failed", Toast.LENGTH_LONG).show()
                Log.d("myError", t.message.toString())
            }

        })
    }

    private fun showVideoError() {
        Toast.makeText(this@MovieDetailsActivity, "Video not found", Toast.LENGTH_LONG).show()
    }

    private fun getDetailsOfMovie(idWithStr: String) {

        val id = idWithStr.substring(4, idWithStr.length)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MovieDetailsInterface::class.java)

        val retrofitData = retrofitBuilder.getDetails(id)
        retrofitData.enqueue(object : Callback<movieDetails> {
            override fun onResponse(call: Call<movieDetails>, response: Response<movieDetails>) {

                responseBody = response.body() !!

                text.text = responseBody.title
                description.text = responseBody.overview
                popularity.text = responseBody.popularity.toString()
                release_date.text = responseBody.release_date
                movie_poster.load("https://image.tmdb.org/t/p/original/" + responseBody.poster_path)

                detailsContent.visibility = View.VISIBLE
                spin_kit.visibility = View.GONE
                getIsFavorite()

            }

            override fun onFailure(call: Call<movieDetails>, t: Throwable) {
                Toast.makeText(this@MovieDetailsActivity, "failed", Toast.LENGTH_LONG).show()
                Log.d("myError", t.message.toString())
            }

        })
    }

    fun changeVisibilityOfStar(previous: View, new: View) {
        new.visibility = View.VISIBLE
        previous.visibility = View.GONE

    }

    fun getIsFavorite() {
        val favoriteMovieList = movieDbHandler.viewFavoriteMovies()
        var isFavorite:Boolean = false

        for(item in favoriteMovieList) {
            Log.d("checkingId", item.id.toString() + " " + responseBody.id.toString())
            if(item.id == responseBody.id) {
                changeVisibilityOfStar(star, fillStar)
                isFavorite = true
                break
            }
        }

        if(!isFavorite) { changeVisibilityOfStar(fillStar, star) }
    }

    override fun onClick(position: Int) {
//        val intent = Intent(this, MovieDetailsActivity::class.java)
//        intent.putExtra("id", moviesData[position].id.toString())
//        startActivity(intent)
    }
}