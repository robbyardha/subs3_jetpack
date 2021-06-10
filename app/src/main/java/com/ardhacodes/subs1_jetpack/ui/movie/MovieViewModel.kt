package com.ardhacodes.subs1_jetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy

class MovieViewModel(val movieRepository: MovTvRepository) : ViewModel(){
    fun getdDataMovie () :List<MovieTvEntity> = MoviesTvDataDummy.DataMovies()

    fun getDataMovieApi () : LiveData<List<MovieTvEntity>> = movieRepository.getPopularMovies()
}