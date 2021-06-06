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
import com.ardhacodes.subs1_jetpack.databinding.ItemTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_tv.view.*

class TvAdapter(private val callback:TvFragmentCallback) : PagedListAdapter<TvEntity, TvAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private val listTv = ArrayList<MovieTvEntity>()
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>(){
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.idtv == newItem.idtv
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.idtv == newItem.idtv
            }

        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: TvEntity) {
            with(itemView) {
                data.poster_path?.let {
                    Glide.with(itemView.context)
                        .load(path + image_w185 + it)
                        .apply(RequestOptions())
                        .into(iv_poster)
                }
                item_title.text = data.title
                item_genre.text = "Release : ${data.release_date}"
                item_yearrelease.text = "Vote Average : ${data.vote_average}"
                item_score.text = "Popularity : ${data.popularity}"
                itemView.setOnClickListener {
                    callback.onItemClicked(data)
                }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }




}