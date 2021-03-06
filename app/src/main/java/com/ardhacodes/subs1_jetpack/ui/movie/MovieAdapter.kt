package com.ardhacodes.subs1_jetpack.ui.movie

import android.content.Intent
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemMovBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(CALLBACK_UTILITY) {
    private var listMovie = ArrayList<MovieTvEntity>()
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

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

    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovies(movies: List<MovieTvEntity>) {
        if (movies == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val itemMovieBindings =
            ItemMovBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBindings)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        } else {

        }
    }

//    override fun getItemCount(): Int {
//        return listMovie.size
//    }

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

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}