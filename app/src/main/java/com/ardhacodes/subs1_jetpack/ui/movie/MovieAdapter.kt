package com.ardhacodes.subs1_jetpack.ui.movie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemMovBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.item_tv.view.*

//class MovieAdapter(val callback: CallbackMovTv) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(CallbackAdapter) {

    private var listMovie = ArrayList<MovieEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    companion object {
        private val CallbackAdapter = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.idmovie == newItem.idmovie
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.idmovie == newItem.idmovie
            }

        }
    }


    inner class MovieViewHolder(private val binding: ItemMovBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity) {
            with(binding) {
                itemTitle.text = data.title
                itemGenre.text = "Release : ${data.release_date}"
                itemYearrelease.text = "Vote Average : ${data.vote_average}"
                itemScore.text = "Popularity : ${data.popularity}"

                Glide.with(itemView.context)
                    .load(path + image_w185 + data.poster_path)
                    .apply(RequestOptions())
                    .into(ivPoster)
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data.idmovie) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }


    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }

}