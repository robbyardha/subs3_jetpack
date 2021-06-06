package com.ardhacodes.subs1_jetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemMovBinding
import com.ardhacodes.subs1_jetpack.databinding.ItemTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter : PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(CallbackDiffUtil) {
    private val listTv = ArrayList<MovieTvEntity>()
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    companion object {
        private val CallbackDiffUtil = object : DiffUtil.ItemCallback<TvEntity>(){
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.idtv == newItem.idtv
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.idtv == newItem.idtv
            }

        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TvViewHolder(private val binding: ItemTvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvEntity) {
            with(binding) {
                itemTitle.text = data.title
                itemGenre.text = "Release : ${data.release_date}"
                itemYearrelease.text = "Vote Average : ${data.vote_average}"
                itemScore.text = "Popularity : ${data.popularity}"

                com.bumptech.glide.Glide.with(itemView.context)
                    .load(path + image_w185 + data.poster_path)
                    .apply(RequestOptions())
                    .into(ivPoster)
            }

        }
    }
//    inner class TvViewHolder(private val binding: ItemTvBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(tv: MovieTvEntity) {
//            with(binding) {
//                itemTitle.text = tv.title
//                itemGenre.text = "Release : ${tv.release_date}"
//                itemYearrelease.text = "Vote Average : ${tv.vote_average}"
//                itemScore.text = "Popularity : ${tv.popularity}"
//
//                Glide.with(itemView.context)
//                    .load(path+image_w185+tv.poster_path)
//                    .apply(RequestOptions())
//                    .into(ivPoster)
//
//                itemView.setOnClickListener {
//                    callback.onItemClicked(tv)
//                }
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemTvBinding = ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}
