package com.example.movie_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_details_activity.*
import android.graphics.Typeface
import android.net.Uri
import android.view.View


class Movie_Details_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

        fillStar.visibility = View.GONE

        val movie = intent.getParcelableExtra<movie_model>("movie")
        val imgURl = "https://image.tmdb.org/t/p/original/" + movie?.poster_path
        val videURL = "https://youtube.com/watch?v=" + movie?.videoKey

        text.text = movie?.title
        description.text = movie?.overview
        popularity.text = movie?.popularity.toString()
        release_date.text = movie?.release_date
        movie_poster.load(imgURl)

        star.setOnClickListener() { changeVisibilityOfStar(star, fillStar) }
        fillStar.setOnClickListener() { changeVisibilityOfStar(fillStar, star) }

        trailer.setOnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videURL))
            startActivity(intent)
        }
    }

    fun changeVisibilityOfStar(previous: View, new: View) {
        new.visibility = View.VISIBLE
        previous.visibility = View.GONE
    }
}