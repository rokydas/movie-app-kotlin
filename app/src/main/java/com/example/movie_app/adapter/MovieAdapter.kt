package com.example.movie_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app.R
import com.example.movie_app.models.movies
import kotlinx.android.synthetic.main.movie_activity.view.*

class MovieAdapter(val context: Context, val movieList: List<movies>, val listener: myOnClickListener)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.title
        val image = itemView.image

        init {
            itemView.setOnClickListener() {
                val position = adapterPosition
                listener.onClick(position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.movie_activity, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = movieList[position].title
        var imgURl = "https://image.tmdb.org/t/p/original/" + movieList[position].poster_path
        holder.image.load(imgURl)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface myOnClickListener {
        fun onClick(position: Int) {}
    }

}