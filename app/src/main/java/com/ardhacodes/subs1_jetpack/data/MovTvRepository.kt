package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.source.datalocal.LocalDataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieObjResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvObjResponse
//import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.vo.ApiResponse
import com.ardhacodes.subs1_jetpack.utils.AppExecutors
import com.ardhacodes.subs1_jetpack.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        ): MovTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovTvRepository(remoteData, localDataSource, appExecutors)
            }
    }

    override fun getPopularMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieObjResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieObjResponse>>> =
                remoteDataSource.getPopularMovies()

            override fun saveCallResult(data: List<MovieObjResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        idmovie = response.id,
                        title = response.title,
                        release_date = response.release_date,
                        popularity = response.popularity,
                        overview = response.overview,
                        vote_average = response.vote_average,
                        poster_path = response.poster_path,
                        is_favorite = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieObjResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> =
                localDataSource.getDetailMovie(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
//                data != null && data.runtime == 0 && data.genres == ""
                data != null

            override fun createCall(): LiveData<ApiResponse<MovieObjResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieObjResponse) {
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

    override fun getFavMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getListFavMovies(), config).build()
    }

    override fun setFavMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavMovies(movie, state)
        }
    }

    override fun getTv(sort: String): LiveData<Resource<PagedList<TvEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvEntity>, List<TvObjResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllTvList(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvObjResponse>>> =
                remoteDataSource.getTvList()


            override fun saveCallResult(data: List<TvObjResponse>) {
                val TvList = ArrayList<TvEntity>()
                for (response in data) {
                    val tventy = TvEntity(
                        idtv = response.id,
                        title = response.title,
                        release_date = response.release_date,
                        popularity = response.popularity,
                        overview = response.overview,
                        vote_average = response.vote_average,
                        poster_path = response.poster_path,
                        is_favorite = false
                    )
                    TvList.add(tventy)
                }
                localDataSource.insertTv(TvList)
            }
        }.asLiveData()
    }

    override fun getTvDetail(tvShowId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvObjResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<TvEntity> = localDataSource.getDetailTv(tvShowId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data != null

            override fun createCall(): LiveData<ApiResponse<TvObjResponse>> =
                remoteDataSource.getTvDetail(tvShowId)

            override fun saveCallResult(data: TvObjResponse) {
                val tvDetail = TvEntity(
                    idtv = data.id,
                    title = data.title,
                    release_date = data.release_date,
                    popularity = data.popularity,
                    overview = data.overview,
                    vote_average = data.vote_average,
                    poster_path = data.poster_path,
                    is_favorite = false
                )
                localDataSource.UpdateDataTv(tvDetail, false)
            }
        }.asLiveData()
    }

    override fun getFavTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getListFavTvs(), config).build()
    }

    override fun setFavTv(tvShow: TvEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavTv(tvShow, state)
        }
    }
}
}