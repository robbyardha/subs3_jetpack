package com.ardhacodes.subs1_jetpack.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiConfig
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiService
import com.ardhacodes.subs1_jetpack.data.source.remote.response.*
import com.ardhacodes.subs1_jetpack.data.source.remote.vo.ApiResponse
import com.ardhacodes.subs1_jetpack.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource {
    companion object {
        @Volatile
        private var instanceRemotes: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource {
            return instanceRemotes ?: synchronized(this) {
                instanceRemotes ?: RemoteDataSource()
            }
        }
    }

    fun getPopularMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.CountIncrement()
        val resMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val clientHttp = ApiConfig.getApiService().getPopularMovie()

        clientHttp.enqueue(object : Callback<ForResponse<MovieResponse>> {
            override fun onResponse(
                call: Call<ForResponse<MovieResponse>>,
                response: Response<ForResponse<MovieResponse>>
            ) {
                resMovies.value = ApiResponse.success(response.body()?.result as List<MovieResponse>)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<ForResponse<MovieResponse>>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }


        })

        return resMovies
    }

    fun getDetailMovie(movieId: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.CountIncrement()
        val resDetailMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        val clientHttp = ApiConfig.getApiService().getDetailMovie(movieId)

        clientHttp.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                resDetailMovie.value = ApiResponse.success(response.body() as MovieResponse)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovieDetail onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })
        return resDetailMovie
    }

//    fun getPopularTv(): LiveData<ApiResponse<List<TvResponse>>> {
//        EspressoIdlingResource.CountIncrement()
////        ApiConfig.instance.getPopularMovie().await().isSuccessful
//        val resTvResponse = MutableLiveData<ApiResponse<List<TvResponse>>>()
//        val coroutineScope = CoroutineScope(Dispatchers.IO)
//        coroutineScope.launch {
//            try {
//                val response = apiService.getTvPopular().await()
//                resTvResponse.postValue(ApiResponse.success(response.result!!))
//            } catch (e: IOException) {
//                e.printStackTrace()
//                resTvResponse.postValue(
//                    ApiResponse.error(
//                        e.message.toString(),
//                        mutableListOf()
//                    )
//                )
//            }
//        }
//        EspressoIdlingResource.CountDecrement()
//        return resTvResponse
//    }

    fun getPopularTv(): LiveData<ApiResponse<List<TvResponse>>> {
        EspressoIdlingResource.CountIncrement()
        val resTv = MutableLiveData<ApiResponse<List<TvResponse>>>()
        val clientHttp = ApiConfig.getApiService().getTvPopular()

        clientHttp.enqueue(object : Callback<ForResponse<TvResponse>> {
            override fun onResponse(
                call: Call<ForResponse<TvResponse>>,
                response: Response<ForResponse<TvResponse>>
            ) {
                resTv.value = ApiResponse.success(response.body()?.result as List<TvResponse>)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<ForResponse<TvResponse>>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })
        return resTv
    }

    fun getDetailTv(tvId: Int): LiveData<ApiResponse<TvResponse>> {
        EspressoIdlingResource.CountIncrement()
        val resDetailTv = MutableLiveData<ApiResponse<TvResponse>>()
        val clientHttp = ApiConfig.getApiService().getDetailTvShow(tvId)

        clientHttp.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                resDetailTv.value = ApiResponse.success(response.body() as TvResponse)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovieDetail onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })
        return resDetailTv
    }

}