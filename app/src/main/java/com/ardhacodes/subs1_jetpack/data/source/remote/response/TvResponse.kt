package com.ardhacodes.subs1_jetpack.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var title: String,

    @SerializedName("first_air_date")
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