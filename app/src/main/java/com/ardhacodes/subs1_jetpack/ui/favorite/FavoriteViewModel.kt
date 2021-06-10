package com.ardhacodes.subs1_jetpack.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(val movTvRepository: MovTvRepository) : ViewModel() {

    fun getListFavoriteMovie(): LiveData<PagedList<MovieEntity>>{
        return movTvRepository.getListFavoriteMovie()
    }

    fun getListFavoriteTvShow(): LiveData<PagedList<TvEntity>> {
        return movTvRepository.getListFavoriteTv()
    }
}