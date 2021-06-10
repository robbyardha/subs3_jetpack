package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DummyMovTvRepository(private val remote: RemoteDataSource) : MovTvDataSource {
    override fun getPopularMovies(): LiveData<List<MovieTvEntity>> {
        val listMovRes = MutableLiveData<List<MovieTvEntity>>()
        val Coroutinescp = CoroutineScope(Dispatchers.IO)
        Coroutinescp.launch {
            remote.getPopularMovies(object : RemoteDataSource.LoadPopularMoviesCallback{
                override fun responseAllMovie(movieResponse: List<MovieResponse>) {
                    val movieList = ArrayList<MovieTvEntity>()
                    for (response in movieResponse){
                        val movie = MovieTvEntity(
                            response.id,
                            response.title,
                            response.release_date,
                            response.popularity,
                            response.overview,
                            response.vote_average,
                            response.poster_path
                        )

                        movieList.add(movie)
                    }
                    listMovRes.postValue(movieList)
                }
            })
        }
        return listMovRes
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieTvEntity>
    {
        val movResultDetail = MutableLiveData<MovieTvEntity>()
        val Coroutinescp = CoroutineScope(Dispatchers.IO)
        Coroutinescp.launch {
            remote.getMovieDetail(movieId, object : RemoteDataSource.LoadMovieDetailCallback{
                override fun responseDetailMovie(movieResponse: MovieResponse) {
                    val movie = MovieTvEntity(
                        movieResponse.id,
                        movieResponse.title,
                        movieResponse.release_date,
                        movieResponse.popularity,
                        movieResponse.overview,
                        movieResponse.vote_average,
                        movieResponse.poster_path
                    )
                    movResultDetail.postValue(movie)
                }
            })
        }
        return movResultDetail
    }

    override fun getTv(): LiveData<List<MovieTvEntity>> {
        val listTvRes = MutableLiveData<List<MovieTvEntity>>()
        val Coroutinescp = CoroutineScope(Dispatchers.IO)
        Coroutinescp.launch {
            remote.getTvList(object : RemoteDataSource.LoadTvCallback{
                override fun responseAllTv(tvShowResponse: List<TvResponse>) {
                    val tvListArr = ArrayList<MovieTvEntity>()
                    for (response in tvShowResponse){
                        val tvRes = MovieTvEntity(
                            response.id,
                            response.title,
                            response.release_date,
                            response.popularity,
                            response.overview,
                            response.vote_average,
                            response.poster_path
                        )
                        tvListArr.add(tvRes)
                    }
                    listTvRes.postValue(tvListArr)
                }
            })
        }
        return listTvRes
    }

    override fun getTvDetail(tvShowId: Int): LiveData<MovieTvEntity>
    {
        val tvResultDetail = MutableLiveData<MovieTvEntity>()
        val Coroutinescp = CoroutineScope(Dispatchers.IO)
        Coroutinescp.launch {
            remote.getTvDetail(tvShowId, object : RemoteDataSource.LoadTvDetailCallback{
                override fun responseDetailTv(tvShowResponse: TvResponse) {
                    val tv = MovieTvEntity(
                        tvShowResponse.id,
                        tvShowResponse.title,
                        tvShowResponse.release_date,
                        tvShowResponse.popularity,
                        tvShowResponse.overview,
                        tvShowResponse.vote_average,
                        tvShowResponse.poster_path
                    )
                    tvResultDetail.postValue(tv)
                }
            })
        }
        return tvResultDetail
    }
}