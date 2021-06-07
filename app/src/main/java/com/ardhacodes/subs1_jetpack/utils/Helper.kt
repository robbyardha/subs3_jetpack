package com.ardhacodes.subs1_jetpack.utils

import android.content.Context
import android.widget.ImageView
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bumptech.glide.Glide

object Helper {
    const val EXTRA_MOVIE = "MOVIE"
    const val EXTRA_TV_SHOW = "TV_SHOW"
    const val URL_API = "https://image.tmdb.org/t/p/"
    const val MOVIE_ENTITIES = "movie_entities"
    const val TV_SHOW_ENTITIES = "tv_show_entities"

    fun setImageGlide(context: Context, imagePath: String, imageView: ImageView){
        Glide.with(context).clear(imageView)
        Glide.with(context).load(imagePath).into(imageView)
    }

    fun getQuery(table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
//        when (filter) {
//            VOTE_BEST -> simpleQuery.append("ORDER BY voteAverage DESC")
//            VOTE_WORST -> simpleQuery.append("ORDER BY voteAverage ASC")
//            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
//        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}