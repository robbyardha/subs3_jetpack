package com.ardhacodes.subs1_jetpack.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val movTvRepository: MovTvRepository) : ViewModel() {

    fun getListPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movTvRepository.getPopularMovies()

    fun getListPopularTv(): LiveData<Resource<PagedList<TvEntity>>> = movTvRepository.getTv()

}