package com.ardhacodes.subs1_jetpack.ui.favorite.tvfavorite

import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity

class TvFavViewModel(val movTvRepository: MovTvRepository) : ViewModel() {
    fun getFavTv() = movTvRepository.getFavTv()

    fun setFavTv(tvEntity: TvEntity) {
        val newState = !tvEntity.is_favorite
        movTvRepository.setFavTv(tvEntity, newState)
    }
}