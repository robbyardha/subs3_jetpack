package com.ardhacodes.subs1_jetpack.data.source.datalocal

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.room.MovieTvDao
import com.ardhacodes.subs1_jetpack.utils.Helper
import com.ardhacodes.subs1_jetpack.utils.SortUtils
import com.ardhacodes.subs1_jetpack.utils.SortUtils.MOVIE_ENTITIES
import com.ardhacodes.subs1_jetpack.utils.SortUtils.TV_SHOW_ENTITIES


//class LocalDataSource private constructor(private val mMovieTvDao: MovieTvDao) {
class LocalDataSource(private val modMovieTvDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource = INSTANCE ?: getInstance(movieTvDao)
    }

/*Movies*/
    fun getAllMovies(sort: String): DataSource.Factory<Int, MovieEntity> = modMovieTvDao.getListMovie(SortUtils.getSortedQuery(sort, MOVIE_ENTITIES))

//    fun getListMovies() : DataSource.Factory<Int, MovieEntity> {
//        return mMovieTvDao.getListMovie(Helper.getQuery(MOVIE_ENTITIES))
//    }

    fun getListFavMovies() : DataSource.Factory<Int, MovieEntity>{
        return modMovieTvDao.getListMovieFavorite()
    }

    fun getDetailMovie(idmovie: Int) : LiveData<MovieEntity> {
        return modMovieTvDao.getDetailMovieById(idmovie)
    }

    fun insertMoviesFav(movies: List<MovieEntity>) = modMovieTvDao.insertFavMovies(movies)

    fun insertMovies(mov: List<MovieEntity>) {
        return modMovieTvDao.insertFavMovies(mov)
    }

    fun UpdateDataMovies(mov : MovieEntity, isTrue: Boolean) {
        mov.is_favorite = isTrue
        modMovieTvDao.updateFavMovie(mov)
    }
    fun setFavMovies(mov : MovieEntity, isTrue: Boolean) {
        mov.is_favorite = isTrue
        modMovieTvDao.updateFavMovie(mov)
    }

//    Tv
//    fun getListTvs() : DataSource.Factory<Int, TvEntity> {
//        return modMovieTvDao.getListTv(Helper.getQuery(SortUtils.TV_SHOW_ENTITIES))
//    }

    fun getAllTvList(sort: String): DataSource.Factory<Int, TvEntity> = modMovieTvDao.getListTv(SortUtils.getSortedQuery(sort, TV_SHOW_ENTITIES))


    fun getListFavTvs() : DataSource.Factory<Int, TvEntity>{
        return modMovieTvDao.getListTvFavorite()
    }

    fun getDetailTv(idtv: Int) : LiveData<TvEntity> {
        return modMovieTvDao.getDetailTvById(idtv)
    }

    fun insertTv(tv: List<TvEntity>) {
        return modMovieTvDao.insertFavTv(tv)
    }

    fun UpdateDataTv(tv : TvEntity, isTrue: Boolean) {
        tv.is_favorite = isTrue
        modMovieTvDao.updateFavTv(tv)
    }
    fun setFavTv(tv : TvEntity, isTrue: Boolean) {
        tv.is_favorite = isTrue
        modMovieTvDao.updateFavTv(tv)
    }
}