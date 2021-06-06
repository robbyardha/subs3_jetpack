package com.ardhacodes.subs1_jetpack.injection

import android.content.Context
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.datalocal.LocalDataSource
import com.ardhacodes.subs1_jetpack.data.source.datalocal.room.MovieTvDatabase
import com.ardhacodes.subs1_jetpack.data.source.remote.RemoteDataSource
import com.ardhacodes.subs1_jetpack.utils.AppExecutors

object Injection {
    fun provideMovTvRepository(context: Context): MovTvRepository {
        val db = MovieTvDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(db.MovieTvDao())
        val appExecutors = AppExecutors()
        return MovTvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}