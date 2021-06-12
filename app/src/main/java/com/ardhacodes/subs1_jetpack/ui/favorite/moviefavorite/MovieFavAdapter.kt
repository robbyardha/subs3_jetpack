package com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemMovBinding
import com.ardhacodes.subs1_jetpack.ui.movie.MovieAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieFavAdapter : PagedListAdapter<MovieEntity, MovieFavAdapter.MovieViewHolder>(CALLBACK_UTILITY) {
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"
    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private val CALLBACK_UTILITY = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.idmovie == newItem.idmovie
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
    inner class MovieViewHolder(private val binding: ItemMovBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                itemTitle.text = movie.title
                itemGenre.text = "Release : ${movie.release_date}"
                itemYearrelease.text = "Vote Average : ${movie.vote_average}"
                itemScore.text = "Popularity : ${movie.popularity}"

                Glide.with(itemView.context)
                    .load(path + image_w185 + movie.poster_path)
                    .apply(RequestOptions())
                    .into(ivPoster)
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie.idmovie) }
//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(movie)
//            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}