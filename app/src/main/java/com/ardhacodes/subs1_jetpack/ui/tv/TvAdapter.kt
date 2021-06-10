package com.ardhacodes.subs1_jetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.databinding.ItemTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter(private val callback:CallbackMovTv) : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private val listTv = ArrayList<MovieTvEntity>()
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    inner class TvViewHolder(private val binding: ItemTvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: MovieTvEntity) {
            with(binding) {
                itemTitle.text = tv.title
                itemGenre.text = "Release : ${tv.release_date}"
                itemYearrelease.text = "Vote Average : ${tv.vote_average}"
                itemScore.text = "Popularity : ${tv.popularity}"

                Glide.with(itemView.context)
                    .load(path+image_w185+tv.poster_path)
                    .apply(RequestOptions())
                    .into(ivPoster)

                itemView.setOnClickListener {
                    callback.onItemClicked(tv)
                }
            }
        }
    }


    fun setTv(tv: List<MovieTvEntity>?) {
        if (tv == null) return
        this.listTv.clear()
        this.listTv.addAll(tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvAdapter.TvViewHolder {
        val itemTvBind = ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemTvBind)
    }

    override fun onBindViewHolder(holder: TvAdapter.TvViewHolder, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)
    }

    override fun getItemCount(): Int {
        return listTv.size
    }
}