package com.example.movie_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.movie_app.R

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

//        fillStar.visibility = View.GONE

        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("id")
        Toast.makeText(this, "$id", Toast.LENGTH_LONG).show()
//        val imgURl = "https://image.tmdb.org/t/p/original/" + movie?.poster_path
//        val videURL = "https://youtube.com/watch?v=" + movie?.videoKey
//
//        text.text = movie?.title
//        description.text = movie?.overview
//        popularity.text = movie?.popularity.toString()
//        release_date.text = movie?.release_date
//        movie_poster.load(imgURl)
//
//        star.setOnClickListener() { changeVisibilityOfStar(star, fillStar) }
//        fillStar.setOnClickListener() { changeVisibilityOfStar(fillStar, star) }
//
//        trailer.setOnClickListener() {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videURL))
//            startActivity(intent)
//        }
    }

    fun changeVisibilityOfStar(previous: View, new: View) {
        new.visibility = View.VISIBLE
        previous.visibility = View.GONE

    }
}