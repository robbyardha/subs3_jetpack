package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.source.datalocal.LocalDataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.vo.ApiResponse
import com.ardhacodes.subs1_jetpack.utils.AppExecutors
import com.ardhacodes.subs1_jetpack.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
class MovTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovTvDataSource {
    companion object {
        @Volatile
        private var instance: MovTvRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovTvRepository {
            return instance ?: synchronized(this) {
                instance ?: MovTvRepository(remoteData, localDataSource, appExecutors)
            }
        }

    }

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>() {
//            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder().apply {
                    var SizeHint = 4
                    var pageSize = 4
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(SizeHint)
                    setPageSize(pageSize)
                }.build()
                return LivePagedListBuilder(localDataSource.getListMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getPopularMovies()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movListArr = ArrayList<MovieEntity>()
                for (item in data) {
                    val movie = MovieEntity(
                        idmovie = item.id,
                        title = item.title,
                        release_date = item.release_date,
                        popularity = item.popularity,
                        overview = item.overview,
                        vote_average = item.vote_average,
                        poster_path = item.poster_path,
                        is_favorite = false
                    )
                    movListArr.add(movie)
                }

                localDataSource.insertMovies(movListArr)
            }

        }.asLiveData()
    }


    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>() {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getDetailMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data != null
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                return remoteDataSource.getDetailMovie(movieId)
            }

            override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    idmovie = data.id,
                    title = data.title,
                    release_date = data.release_date,
                    popularity = data.popularity,
                    overview = data.overview,
                    vote_average = data.vote_average,
                    poster_path = data.poster_path,
                    is_favorite = false
                )
                localDataSource.UpdateDataMovies(movie, false)
            }
        }.asLiveData()
    }

    override fun getListFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder().apply {
            var SizeHint = 4
            var pageSize = 4
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(SizeHint)
            setPageSize(pageSize)
        }.build()
        return LivePagedListBuilder(localDataSource.getListFavMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, isFav: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavMovies(movie, isFav)
        }
    }

    override fun getPopularTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>() {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder().apply {
                    var SizeHint = 4
                    var pageSize = 4
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(SizeHint)
                    setPageSize(pageSize)
                }.build()
                return LivePagedListBuilder(localDataSource.getListTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> {
                return remoteDataSource.getPopularTv()
            }

            override fun saveCallResult(data: List<TvResponse>) {
                val tvListArr = ArrayList<TvEntity>()
                for (item in data) {
                    val tv = TvEntity(
                        idtv = item.id,
                        title = item.title,
                        release_date = item.release_date,
                        popularity = item.popularity,
                        overview = item.overview,
                        vote_average = item.vote_average,
                        poster_path = item.poster_path,
                        is_favorite = false
                    )
                    tvListArr.add(tv)
                }

                localDataSource.insertTv(tvListArr)
            }

        }.asLiveData()
    }


    override fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvResponse>() {
            override fun loadFromDB(): LiveData<TvEntity> {
                return localDataSource.getDetailTv(tvId)
            }

            override fun shouldFetch(data: TvEntity?): Boolean {
                return data != null
            }

            override fun createCall(): LiveData<ApiResponse<TvResponse>> {
                return remoteDataSource.getDetailTv(tvId)
            }

            override fun saveCallResult(data: TvResponse) {
                val tv = TvEntity(
                    idtv = data.id,
                    title = data.title,
                    release_date = data.release_date,
                    popularity = data.popularity,
                    overview = data.overview,
                    vote_average = data.vote_average,
                    poster_path = data.poster_path,
                    is_favorite = false
                )
                localDataSource.UpdateDataTv(tv, false)
            }
        }.asLiveData()
    }

    override fun getListFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder().apply {
            var SizeHint = 4
            var pageSize = 4
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(SizeHint)
            setPageSize(pageSize)
        }.build()
        return LivePagedListBuilder(localDataSource.getListFavTvs(), config).build()
    }

    override fun setFavoriteTv(tv: TvEntity, isFav: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavTv(tv, isFav)
        }
    }
}
