package com.ardhacodes.subs1_jetpack.injection

import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource

object Injection {
    fun provideCatalogRepository(): MovTvRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return MovTvRepository.getInstance(remoteDataSource)
    }
}