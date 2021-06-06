package com.ardhacodes.subs1_jetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.vo.Resource
import javax.inject.Inject

//class MovieViewModel(val movieRepository: MovTvRepository) : ViewModel(){
class MovieViewModel @Inject constructor(val movTvRepository: MovTvRepository) : ViewModel(){
//    fun getdDataMovie () :List<MovieTvEntity> = MoviesTvDataDummy.DataMovies()
//
//    fun getDataMovieApi () : LiveData<List<MovieTvEntity>> = movieRepository.getPopularMovies()

    fun getListNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movTvRepository.getPopularMovies()
}