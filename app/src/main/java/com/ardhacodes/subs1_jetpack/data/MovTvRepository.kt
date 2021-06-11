package com.ardhacodes.subs1_jetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.source.datalocal.LocalDataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
//import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import com.ardhacodes.subs1_jetpack.utils.AppExecutors
import com.ardhacodes.subs1_jetpack.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): MovTvDataSource{
    companion object {
        @Volatile
        private var instance: MovTvRepository? = null
        fun getInstance(remoteData: RemoteDataSource, localDataSource: LocalDataSource, appExecutors: AppExecutors): MovTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovTvRepository(remoteData, localDataSource, appExecutors)
            }
    }

}