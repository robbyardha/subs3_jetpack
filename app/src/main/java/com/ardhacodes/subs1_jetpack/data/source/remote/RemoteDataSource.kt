package com.ardhacodes.subs1_jetpack.data.source.remote

import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiConfig
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.utils.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this)
        {
            instance ?: RemoteDataSource()
        }
    }

    suspend fun getPopularMovies(callback: LoadPopularMoviesCallback) {
        EspressoIdlingResource.CountIncrement()
//        ApiConfig.instance.getPopularMovie().await().isSuccessful
        var intanceApiConfig = ApiConfig.instanceApiService
        intanceApiConfig.getPopularMovie().await().result?.let { getlistMovie ->
            callback.responseAllMovie(
                getlistMovie
            )
            EspressoIdlingResource.CountDecrement()
        }
    }

    suspend fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.CountIncrement()
        var intanceApiConfig = ApiConfig.instanceApiService
        intanceApiConfig.getDetailMovie(movieId).await().let { getdetmovie ->
            callback.responseDetailMovie(
                getdetmovie
            )
            EspressoIdlingResource.CountDecrement()
        }
    }

    suspend fun getTvList(callback: LoadTvCallback) {
        EspressoIdlingResource.CountIncrement()
        var intanceApiConfig = ApiConfig.instanceApiService
        intanceApiConfig.getTvPopular().await().result?.let { getlistTvShow ->
            callback.responseAllTv(
                getlistTvShow
            )
            EspressoIdlingResource.CountDecrement()
        }
    }


    suspend fun getTvDetail(tvShowId: Int, callback: LoadTvDetailCallback) {
        EspressoIdlingResource.CountIncrement()
        var intanceApiConfig = ApiConfig.instanceApiService
        intanceApiConfig.getDetailTvShow(tvShowId).await().let { getdettvShow ->
            callback.responseDetailTv(
                getdettvShow
            )
            EspressoIdlingResource.CountDecrement()

        }
    }


    interface LoadPopularMoviesCallback {
        fun responseAllMovie(movieResponse: List<MovieResponse>)
    }

    interface LoadMovieDetailCallback {
        fun responseDetailMovie(movieResponse: MovieResponse)
    }


    interface LoadTvCallback {
        fun responseAllTv(tvShowResponse: List<TvResponse>)
    }

    interface LoadTvDetailCallback {
        fun responseDetailTv(tvShowResponse: TvResponse)
    }
}