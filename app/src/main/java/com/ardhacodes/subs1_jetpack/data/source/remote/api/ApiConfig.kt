package com.ardhacodes.subs1_jetpack.data.source.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val baseUrl = "https://api.themoviedb.org/3/"

    val clientHttp = OkHttpClient.Builder().apply {

    }.build()

    val retrofitBuild: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            client(clientHttp)
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
        }
    }
    val instanceApiService: ApiService by lazy {
        retrofitBuild
            .build()
            .create(ApiService::class.java)
    }


}