package com.example.movie_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_app.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        explore_btn.setOnClickListener{
            val intentMovies = Intent(this, MoviesActivity::class.java)
            startActivity(intentMovies)
        }
    }
}