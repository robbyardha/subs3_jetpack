package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity

interface MovTvDataSource {
    fun getPopularMovies(): LiveData<List<MovieTvEntity>>

    fun getMovieDetail(movieId: Int): LiveData<MovieTvEntity>

    fun getTv(): LiveData<List<MovieTvEntity>>

    fun getTvDetail(tvShowId: Int): LiveData<MovieTvEntity>
}