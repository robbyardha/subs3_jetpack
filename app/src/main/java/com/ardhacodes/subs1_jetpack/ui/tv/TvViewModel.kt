package com.ardhacodes.subs1_jetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy

class TvViewModel(val movTvRepository: MovTvRepository):ViewModel() {
    fun getdDataTv () :List<MovieTvEntity> = MoviesTvDataDummy.DataTvShow()
    fun getDataTvAPI(sort: String) = movTvRepository.getTv(sort)
//    fun getDataTvAPI() : LiveData<List<MovieTvEntity>>
//    {
//       return TvRepository.getTv()
//    }
}