package com.ardhacodes.subs1_jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.injection.Injection
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.ui.tv.TvViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movTvRepository: MovTvRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movTvRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(movTvRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movTvRepository) as T
            }
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
//                DetailViewModel(movTvRepository) as T
//            }

            else -> throw Throwable("Unknown Viewodel class:" + modelClass.name)
        }


}