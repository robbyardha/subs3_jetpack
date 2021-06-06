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
import com.ardhacodes.subs1_jetpack.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
class MovTvRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource
) : MovTvDataSource {
    override fun setFavoriteMovie(movie: MovieEntity) {
        var coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            localDataSource.setFavoriteMovies(movie)
        }
    }

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>() {
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
                        null,
                        item.id,
                        item.title,
                        item.release_date,
                        item.popularity,
                        item.overview,
                        item.vote_average,
                        item.poster_path,
                        false
                    )
                    movListArr.add(movie)
                }

                localDataSource.insertMovies(movListArr)
            }

        }.asLiveData()
    }


    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> {
        return localDataSource.getDetailMovie(movieId)
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

    override fun setFavoriteTv(tv: TvEntity) {
        var coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            localDataSource.setFavoriteTv(tv)
        }
    }

    override fun getTv(): LiveData<Resource<PagedList<TvEntity>>> {
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
                        null,
                        item.id,
                        item.title,
                        item.release_date,
                        item.popularity,
                        item.overview,
                        item.vote_average,
                        item.poster_path,
                        false
                    )
                    tvListArr.add(tv)
                }

                localDataSource.insertTv(tvListArr)
            }

        }.asLiveData()
    }

    override fun getTvDetail(tvShowId: Int): LiveData<TvEntity> {
        return localDataSource.getDetailTv(tvShowId)
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
}
