package com.ardhacodes.subs1_jetpack.network

import com.ardhacodes.subs1_jetpack.data.source.remote.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule() {
    companion object {
        private var base_url_movie = "https://api.themoviedb.org/3/"
        val conntimeout : Long = 30

        @Singleton
        @Provides
        fun provHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(conntimeout, TimeUnit.SECONDS)
            readTimeout(conntimeout, TimeUnit.SECONDS)
            writeTimeout(conntimeout, TimeUnit.SECONDS)
        }.build()

        @Singleton
        @Provides
        fun provRetrofitLibInstance(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().apply {
                baseUrl(base_url_movie)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }


        @Provides
        fun provApi(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

    }
}