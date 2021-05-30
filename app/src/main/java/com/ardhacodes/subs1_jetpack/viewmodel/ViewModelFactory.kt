package com.ardhacodes.subs1_jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.injection.Injection
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.ui.tv.TvViewModel

class ViewModelFactory private constructor(private val mCatalogRepository: MovTvRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideCatalogRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mCatalogRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(mCatalogRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mCatalogRepository) as T
            }

            else -> throw Throwable("Unknown Viewodel class:" + modelClass.name)
        }
}