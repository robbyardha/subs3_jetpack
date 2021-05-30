package com.ardhacodes.subs1_jetpack.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String,

    @SerializedName("release_date")
    var release_date: String,

    @SerializedName("popularity")
    var popularity: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("vote_average")
    var vote_average: String,

    @SerializedName("poster_path")
    var poster_path: String,


    )