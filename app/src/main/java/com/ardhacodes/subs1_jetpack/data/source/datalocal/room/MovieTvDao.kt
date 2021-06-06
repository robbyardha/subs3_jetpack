package com.ardhacodes.subs1_jetpack.data.source.datalocal.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity

@Dao
interface MovieTvDao {
    //    Module Movie Query
//    @RawQuery(observedEntities = [MovieEntity::class])
//    fun getListMovie(query: SimpleSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM favorite_movies")
    public fun getListMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM favorite_movies WHERE is_favorite = 1")
    public fun getListMovieFavorite(): DataSource.Factory<Int, MovieEntity>
    //0 -> Non Fav | 1-> Fav

    @Query("SELECT * FROM favorite_movies WHERE idmovie = :idmovie")
    fun getDetailMovieById(idmovie: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavMovies(mov: List<MovieEntity>)

    @Update(entity = MovieEntity::class)
    fun updateFavMovie(movie: MovieEntity)


    //    Module Tv Query
//    @RawQuery(observedEntities = [TvEntity::class])
//    fun getListTv(query: SimpleSQLiteQuery): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM favorite_tv")
    public fun getListTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM favorite_tv WHERE is_favorite = 1")
    public fun getListTvFavorite(): DataSource.Factory<Int, TvEntity>
    //0 -> Non Fav | 1-> Fav

    @Query("SELECT * FROM favorite_tv WHERE idtv = :idtv")
    fun getDetailTvById(idtv: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavTv(mov: List<TvEntity>)

    @Update(entity = TvEntity::class)
    fun updateFavTv(movie: TvEntity)

}