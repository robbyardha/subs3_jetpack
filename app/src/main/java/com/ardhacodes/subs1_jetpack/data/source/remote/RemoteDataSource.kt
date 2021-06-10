package com.ardhacodes.subs1_jetpack.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiConfig
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiService
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieObjResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MoviesResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvObjResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.vo.ApiResponse
import com.ardhacodes.subs1_jetpack.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.security.Key

class RemoteDataSource {
    private val KEY = "57d7a07963575b131d0f873f638ec911"
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this)
        {
            instance ?: RemoteDataSource()
        }
    }

    public fun getPopularMovies(): LiveData<ApiResponse<List<MovieObjResponse>>>{
        EspressoIdlingResource.CountIncrement()
        val resMovies = MutableLiveData<ApiResponse<List<MovieObjResponse>>>()
        val client = ApiConfig.getApiService().getPopularMovie(KEY)

        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                resMovies.value = ApiResponse.success(response.body()?.results as List<MovieObjResponse>)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })

        return resMovies
    }

    public fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieObjResponse>>{
        EspressoIdlingResource.CountIncrement()
        val resDetailMov = MutableLiveData<ApiResponse<MovieObjResponse>>()
        val client = ApiConfig.getApiService().getDetailMovie(movieId, KEY)

        client.enqueue(object : Callback<MovieObjResponse> {
            override fun onResponse(call: Call<MovieObjResponse>, response: Response<MovieObjResponse>) {
                resDetailMov.value = ApiResponse.success(response.body() as MovieObjResponse)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<MovieObjResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovieDetail onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })

        return resDetailMov
    }

    public fun getTvList(): LiveData<ApiResponse<List<TvObjResponse>>>{
        EspressoIdlingResource.CountIncrement()
        val resTv = MutableLiveData<ApiResponse<List<TvObjResponse>>>()
        val client = ApiConfig.getApiService().getTvPopular(KEY)

        client.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                resTv.value = ApiResponse.success(response.body()?.results as List<TvObjResponse>)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getTvShows onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })

        return resTv
    }

    public fun getTvDetail(tvShowId: Int): LiveData<ApiResponse<TvObjResponse>>{
        EspressoIdlingResource.CountIncrement()
        val resDetTv = MutableLiveData<ApiResponse<TvObjResponse>>()
        val client = ApiConfig.getApiService().getDetailTvShow(tvShowId, ApiService.KEY)

        client.enqueue(object : Callback<TvObjResponse> {
            override fun onResponse(call: Call<TvObjResponse>, response: Response<TvObjResponse>) {
                resDetTv.value = ApiResponse.success(response.body() as TvObjResponse)
                EspressoIdlingResource.CountDecrement()
            }

            override fun onFailure(call: Call<TvObjResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDetailTvShow onFailure : ${t.message}")
                EspressoIdlingResource.CountDecrement()
            }
        })

        return resDetTv
    }
}