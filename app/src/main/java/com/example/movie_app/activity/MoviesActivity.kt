package com.example.movie_app.activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_app.MovieAdapter
import com.example.movie_app.R
import com.example.movie_app.TopRatedInterface
import com.example.movie_app.models.movies
import com.example.movie_app.models.results
import kotlinx.android.synthetic.main.movies_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesActivity: AppCompatActivity(), MovieAdapter.myOnClickListener {
    var BASE_URL = "https://api.themoviedb.org/3/"
    lateinit var gridLayoutManager: GridLayoutManager
    var moviesData = listOf<movies>()

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
        retrofitData.enqueue(object : Callback<results> {
            override fun onResponse(call: Call<results>, response: Response<results>) {
                spin_kit.setVisibility(View.GONE);
                movies_recycler.setVisibility(View.VISIBLE);

                Toast.makeText(this@MoviesActivity, "success", Toast.LENGTH_LONG).show()

                val responseBody = response.body() !!
                moviesData = responseBody.results

                val movieAdapter = MovieAdapter(baseContext, moviesData, this@MoviesActivity)

                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter

            }

            override fun onFailure(call: Call<results>, t: Throwable) {
                Toast.makeText(this@MoviesActivity, t.message, Toast.LENGTH_LONG).show()
                val builder = AlertDialog.Builder(this@MoviesActivity)

                builder.setTitle("Network issue")
                builder.setMessage("Your network is turned off. Please turn on to load movies...")
                builder.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int -> finish() })
                builder.show()
            }
        })
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("id", moviesData[position].id.toString())
        startActivity(intent)
    }


}

