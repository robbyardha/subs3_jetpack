package com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite

import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity

class MovieFavViewModel(val movTvRepository: MovTvRepository) : ViewModel() {
    public fun getFavMov() = movTvRepository.getFavMovie()

    fun setFavMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.is_favorite
        movTvRepository.setFavMovie(movieEntity, newState)
    }
}