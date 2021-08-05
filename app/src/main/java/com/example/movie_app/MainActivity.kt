package com.example.movie_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        explore_btn.setOnClickListener{
            val intentMovies = Intent(this, Movies_Activity::class.java)
            startActivity(intentMovies)
        }
    }
}