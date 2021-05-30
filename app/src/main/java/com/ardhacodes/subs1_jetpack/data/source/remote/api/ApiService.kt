package com.ardhacodes.subs1_jetpack.data.source.remote.api

import com.ardhacodes.subs1_jetpack.data.source.remote.response.ForResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.MovieResponse
import com.ardhacodes.subs1_jetpack.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val KEY = "57d7a07963575b131d0f873f638ec911"
    }

    @GET("movie/popular")
    public fun getPopularMovie(
        @Query("api_key") apiKey: String = KEY
    ): Call<ForResponse<MovieResponse>>

    @GET("movie/{movie_id}")
    public fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = KEY
    ): Call<MovieResponse>

    @GET("tv/popular")
    public fun getTvPopular(
        @Query("api_key") apiKey: String = KEY
    ): Call<ForResponse<TvResponse>>

    @GET("tv/{tv_id}")
    public fun getDetailTvShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = KEY
    ): Call<TvResponse>
}