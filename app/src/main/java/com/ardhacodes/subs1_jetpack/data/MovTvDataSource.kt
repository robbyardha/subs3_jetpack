package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource

interface MovTvDataSource {
    fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getListFavoriteMovie() : LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, isFav:Boolean)


    fun getPopularTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>>

    fun getListFavoriteTv() : LiveData<PagedList<TvEntity>>

    fun setFavoriteTv(tv: TvEntity, isFav:Boolean)



}