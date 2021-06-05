package com.ardhacodes.subs1_jetpack.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiConfig
import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiService
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.vo.ApiResponse
import com.ardhacodes.subs1_jetpack.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import okio.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {


    fun getPopularMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.CountIncrement()
//        ApiConfig.instance.getPopularMovie().await().isSuccessful
        val resMovResponse = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            try {
                val response = apiService.getPopularMovie().await()
                resMovResponse.postValue(ApiResponse.success(response.result!!))
            } catch (e: IOException) {
                e.printStackTrace()
                resMovResponse.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.CountDecrement()
        return resMovResponse
    }


    fun getPopularTv(): LiveData<ApiResponse<List<TvResponse>>> {
        EspressoIdlingResource.CountIncrement()
//        ApiConfig.instance.getPopularMovie().await().isSuccessful
        val resTvResponse = MutableLiveData<ApiResponse<List<TvResponse>>>()
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            try {
                val response = apiService.getTvPopular().await()
                resTvResponse.postValue(ApiResponse.success(response.result!!))
            } catch (e: IOException) {
                e.printStackTrace()
                resTvResponse.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.CountDecrement()
        return resTvResponse
    }
}