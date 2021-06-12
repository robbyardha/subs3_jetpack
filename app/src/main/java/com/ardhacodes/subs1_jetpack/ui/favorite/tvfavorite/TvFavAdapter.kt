package com.ardhacodes.subs1_jetpack.ui.favorite.tvfavorite

import android.media.tv.TvView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemMovBinding
import com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite.MovieFavAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvFavAdapter : PagedListAdapter<TvEntity, TvFavAdapter.TvViewHolder>(CALLBACK_UTILITY) {
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"
    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private val CALLBACK_UTILITY = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.idtv == newItem.idtv
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TvViewHolder(private val binding: ItemMovBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                itemTitle.text = tv.title
                itemGenre.text = "Release : ${tv.release_date}"
                itemYearrelease.text = "Vote Average : ${tv.vote_average}"
                itemScore.text = "Popularity : ${tv.popularity}"

                Glide.with(itemView.context)
                    .load(path + image_w185 + tv.poster_path)
                    .apply(RequestOptions())
                    .into(ivPoster)
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(tv.idtv) }
//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(movie)
//            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemMovieBinding =
            ItemMovBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}