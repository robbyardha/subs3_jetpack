package com.ardhacodes.subs1_jetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.vo.Resource
import javax.inject.Inject

//class TvViewModel(val TvRepository: MovTvRepository):ViewModel() {
class TvViewModel @Inject constructor(val movTvRepository: MovTvRepository) : ViewModel() {
    fun getdDataTv(): List<MovieTvEntity> = MoviesTvDataDummy.DataTvShow()

    //    fun getDataTvAPI() : LiveData<List<MovieTvEntity>>
//    {
//       return TvRepository.getTv()
//    }
    fun getListPopularTv(): LiveData<Resource<PagedList<TvEntity>>> = movTvRepository.getTv()
}