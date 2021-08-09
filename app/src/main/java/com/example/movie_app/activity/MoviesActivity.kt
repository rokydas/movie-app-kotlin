package com.example.movie_app.activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_app.adapter.MovieAdapter
import com.example.movie_app.MovieDbHandler
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
    var category = "top_rated"

    override fun onResume() {
        super.onResume()
        if(category == "favorite") {
            getFavoriteMovies()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        val options = arrayOf<String>("Top rated movies", "Most popular movies", "Your favourite movies")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , options)

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position == 0) category = "top_rated"
                else if(position == 1) category = "popular"

                if(position == 0 || position == 1) {
                    spin_kit.visibility = View.VISIBLE
                    movies_recycler.visibility = View.GONE
                    getMovies()
                }

                else {
                    category = "favorite"
                    getFavoriteMovies()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MoviesActivity, "nothing is selected", Toast.LENGTH_LONG).show()
            }
        }

        movies_recycler.setVisibility(View.GONE);

        movies_recycler.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        movies_recycler.layoutManager = gridLayoutManager

        getMovies()
    }

    private fun getFavoriteMovies() {
        val movieDbHandler: MovieDbHandler = MovieDbHandler(this)
        val favoriteMovieList = movieDbHandler.viewFavoriteMovies()
        moviesData = favoriteMovieList
        val movieAdapter = MovieAdapter(baseContext, favoriteMovieList, this@MoviesActivity)
        movieAdapter.notifyDataSetChanged()
        movies_recycler.adapter = movieAdapter
    }


    private fun getMovies() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TopRatedInterface::class.java)

        val retrofitData = retrofitBuilder.getMovies(category)
        retrofitData.enqueue(object : Callback<results> {
            override fun onResponse(call: Call<results>, response: Response<results>) {
                spin_kit.setVisibility(View.GONE);
                movies_recycler.setVisibility(View.VISIBLE);

                val responseBody = response.body() !!
                moviesData = responseBody.results

                val movieAdapter = MovieAdapter(baseContext, moviesData, this@MoviesActivity)

                movieAdapter.notifyDataSetChanged()
                movies_recycler.adapter = movieAdapter

            }

            override fun onFailure(call: Call<results>, t: Throwable) {
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

