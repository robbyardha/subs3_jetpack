package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource

interface MovTvDataSource {
    fun getPopularMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavMovie(): LiveData<PagedList<MovieEntity>>

    fun setFavMovie(movie: MovieEntity, state: Boolean)

    fun getTv(sort: String): LiveData<Resource<PagedList<TvEntity>>>

    fun getTvDetail(tvShowId: Int): LiveData<Resource<TvEntity>>

    fun getFavTv(): LiveData<PagedList<TvEntity>>

    fun setFavTv(tvShow: TvEntity, state: Boolean)
}