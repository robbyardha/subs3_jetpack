package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource

interface MovTvDataSource {
    fun setFavoriteMovie(movie: MovieEntity)

    fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>

    fun getListFavoriteMovie() : LiveData<PagedList<MovieEntity>>


    fun setFavoriteTv(tv: TvEntity)

    fun getTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getTvDetail(tvShowId: Int): LiveData<TvEntity>

    fun getListFavoriteTv() : LiveData<PagedList<TvEntity>>

}