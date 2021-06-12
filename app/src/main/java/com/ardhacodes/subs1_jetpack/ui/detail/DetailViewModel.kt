package com.ardhacodes.subs1_jetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource

class DetailViewModel(val modelMovTvRepository: MovTvRepository) : ViewModel() {
    companion object {
        const val MOVIE_VIEWMDL = "movie"
        const val TV_VIEWMDL = "tv"
    }

    private lateinit var detailtv: LiveData<Resource<TvEntity>>
    private lateinit var detailmov: LiveData<Resource<MovieEntity>>

    public fun setMovTvCategory(id: Int, category: String) {
        when (category) {
            TV_VIEWMDL -> {
                detailtv = modelMovTvRepository.getTvDetail(id)
            }

            MOVIE_VIEWMDL -> {
                detailmov = modelMovTvRepository.getMovieDetail(id)
            }
        }
    }

    public fun setterFavMov() {
        val resource = detailmov.value
        if (resource?.data != null) {
            val newState = !resource.data?.is_favorite!!
            modelMovTvRepository.setFavMovie(resource.data!!, newState)
        }
    }

    public fun setterFavTv() {
        val resource = detailtv.value
        if (resource?.data != null) {
            val newState = !resource.data?.is_favorite!!
            modelMovTvRepository.setFavTv(resource.data!!, newState)
        }
    }

    public fun getDetailMovieapis() = detailmov

    public fun getDetailTvapis() = detailtv
}