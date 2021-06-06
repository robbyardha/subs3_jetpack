package com.ardhacodes.subs1_jetpack.data.source.datalocal

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_tv")
@Parcelize
data class TvEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idmovie")
    var idtv : Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "release_date")
    var release_date: String,

    @ColumnInfo(name = "popularity")
    var popularity: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "vote_average")
    var vote_average: String,

    @ColumnInfo(name = "poster_path")
    var poster_path: String,

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var is_favorite: Boolean = false,
): Parcelable