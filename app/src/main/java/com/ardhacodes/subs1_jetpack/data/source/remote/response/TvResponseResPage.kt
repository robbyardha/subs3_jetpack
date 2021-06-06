package com.ardhacodes.subs1_jetpack.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponseResPage(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieResponse>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)