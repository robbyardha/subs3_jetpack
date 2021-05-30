package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovTvRepository private constructor(private val remoteDataSource: RemoteDataSource): MovTvDataSource{
    companion object{
        @Volatile
        private var instance: MovTvRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): MovTvRepository {
            return instance ?: synchronized(this){
                instance ?: MovTvRepository(remoteDataSource)
            }
        }
//        fun getInstance(remoteDataSource: RemoteDataSource): MovTvRepository =
//            instance ?: synchronized(this){
//                instance ?: MovTvRepository(remoteDataSource)
//            }
    }

    override fun getPopularMovies(): LiveData<List<MovieTvEntity>> {
        val listMovieResult = MutableLiveData<List<MovieTvEntity>>()
        val CoroutineScp = CoroutineScope(Dispatchers.IO)
        CoroutineScp.launch {
            remoteDataSource.getPopularMovies(object : RemoteDataSource.LoadPopularMoviesCallback {
                override fun responseAllMovie(movieResponse: List<MovieResponse>) {
                    val movieList = ArrayList<MovieTvEntity>()
                    for(response in movieResponse){
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
                    listMovieResult.postValue(movieList)
                }

            })
        }

        return listMovieResult
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieTvEntity> {
        val movResult = MutableLiveData<MovieTvEntity>()
        val CoroutineScp = CoroutineScope(Dispatchers.IO)
        CoroutineScp.launch {
            remoteDataSource.getMovieDetail(movieId, object: RemoteDataSource.LoadMovieDetailCallback{
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
                    movResult.postValue(movie)
                }
            })
        }
        return movResult
    }

    override fun getTv(): LiveData<List<MovieTvEntity>> {
        val listTvShowResult = MutableLiveData<List<MovieTvEntity>>()
        val CoroutineScp = CoroutineScope(Dispatchers.IO)
        CoroutineScp.launch {
            remoteDataSource.getTvList(object : RemoteDataSource.LoadTvCallback{
                override fun responseAllTv(tvShowResponse: List<TvResponse>) {
                    val tvShowList = ArrayList<MovieTvEntity>()
                    for(response in tvShowResponse){
                        val tvShow = MovieTvEntity(
                            response.id,
                            response.title,
                            response.release_date,
                            response.popularity,
                            response.overview,
                            response.vote_average,
                            response.poster_path
                        )
                        tvShowList.add(tvShow)
                    }
                    listTvShowResult.postValue(tvShowList)
                }
            })
        }
        return listTvShowResult
    }

    override fun getTvDetail(tvShowId: Int): LiveData<MovieTvEntity> {
        val tvShowResult = MutableLiveData<MovieTvEntity>()
        val CoroutineScp = CoroutineScope(Dispatchers.IO)
        CoroutineScp.launch {
            remoteDataSource.getTvDetail(tvShowId, object :  RemoteDataSource.LoadTvDetailCallback {
                override fun responseDetailTv(tvShowResponse: TvResponse) {
                    val tvShow = MovieTvEntity(
                        tvShowResponse.id,
                        tvShowResponse.title,
                        tvShowResponse.release_date,
                        tvShowResponse.popularity,
                        tvShowResponse.overview,
                        tvShowResponse.vote_average,
                        tvShowResponse.poster_path
                    )
                    tvShowResult.postValue(tvShow)
                }
            })
        }
        return tvShowResult
    }

}