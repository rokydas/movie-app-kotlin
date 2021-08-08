package com.example.movie_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import coil.load
import com.example.movie_app.MovieDbHandler
import com.example.movie_app.MovieDetailsInterface
import com.example.movie_app.R
import com.example.movie_app.models.favoriteMovie
import com.example.movie_app.models.movieDetails
import com.example.movie_app.models.movies
import kotlinx.android.synthetic.main.movie_details_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailsActivity : AppCompatActivity() {

    var BASE_URL = "https://api.themoviedb.org/3/"
    var responseBody = movieDetails(0, "", 0.0, "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

        val movieDbHandler: MovieDbHandler = MovieDbHandler(this)

        detailsContent.visibility = View.GONE
        fillStar.visibility = View.GONE

        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("id")

        val idWithStr: String = "id: " + id

        getDetailsOfMovie(idWithStr)

        star.setOnClickListener() {
            val status = movieDbHandler.addFavoriteMovie(favoriteMovie(responseBody.id , responseBody.title, responseBody.poster_path))
            if(status > -1) {
                changeVisibilityOfStar(star, fillStar)
            }
        }

        fillStar.setOnClickListener() {
            val status = movieDbHandler.deleteEmployee(responseBody.id)
            if(status > -1) {
                changeVisibilityOfStar(fillStar, star)
            }
        }

//        trailer.setOnClickListener() {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videURL))
//            startActivity(intent)
//        }
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
}