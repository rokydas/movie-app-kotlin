package com.example.movie_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.movie_activity.view.*

class MovieAdapter(val context: Context, val movieList: List<Result>, val listener:myOnClickListener)
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