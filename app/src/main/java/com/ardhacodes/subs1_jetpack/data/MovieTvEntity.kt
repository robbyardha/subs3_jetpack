package com.ardhacodes.subs1_jetpack.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieTvEntity(
//    var id : String,
//    var title : String,
//    var genre : String,
//    var yearrelease : String,
//    var poster : String,
//    var score : String,
//    var duration: String,
//    var overview: String,
    var id: Int = 0,
    var title: String,
    var release_date: String,
    var popularity: String,
    var overview: String,
    var vote_average: String,
    var poster_path: String,
):Parcelable
