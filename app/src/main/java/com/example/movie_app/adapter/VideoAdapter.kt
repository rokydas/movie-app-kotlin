package com.example.movie_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.R
import com.example.movie_app.models.videoInfo

class VideoAdapter(val context: Context, val videoList: List<videoInfo>, val listener: myOnClickListener)
    : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener() {
                val position = adapterPosition
                listener.onClick(position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.video_activity, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { }

    override fun getItemCount(): Int {
        return videoList.size
    }

    interface myOnClickListener {
        fun onClick(position: Int) {}
    }

}