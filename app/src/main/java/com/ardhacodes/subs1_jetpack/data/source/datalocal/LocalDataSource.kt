package com.ardhacodes.subs1_jetpack.data.source.datalocal

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.room.MovieTvDao
import javax.inject.Inject


//class LocalDataSource private constructor(private val mMovieTvDao: MovieTvDao) {
class LocalDataSource @Inject constructor(private val mMovieTvDao: MovieTvDao) {
//    Movies
    fun getListMovies() : DataSource.Factory<Int, MovieEntity> {
        return mMovieTvDao.getListMovie()
    }

    fun getListFavMovies() : DataSource.Factory<Int, MovieEntity>{
        return mMovieTvDao.getListMovieFavorite()
    }

    fun getDetailMovie(idmovie: Int) : LiveData<MovieEntity> {
        return mMovieTvDao.getDetailMovieById(idmovie)
    }

    fun insertMovies(mov: List<MovieEntity>) {
        return mMovieTvDao.insertFavMovies(mov)
    }

    fun setFavoriteMovies(mov : MovieEntity) {
        mov.is_favorite = !mov.is_favorite
        mMovieTvDao.updateFavMovie(mov)
    }

//    Tv
    fun getListTvs() : DataSource.Factory<Int, TvEntity> {
        return mMovieTvDao.getListTv()
    }

    fun getListFavTvs() : DataSource.Factory<Int, TvEntity>{
        return mMovieTvDao.getListTvFavorite()
    }

    fun getDetailTv(idtv: Int) : LiveData<TvEntity> {
        return mMovieTvDao.getDetailTvById(idtv)
    }

    fun insertTv(tv: List<TvEntity>) {
        return mMovieTvDao.insertFavTv(tv)
    }

    fun setFavoriteTv(tv : TvEntity) {
        tv.is_favorite = !tv.is_favorite
        mMovieTvDao.updateFavTv(tv)
    }
}