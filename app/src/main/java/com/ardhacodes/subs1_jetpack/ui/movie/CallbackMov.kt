package com.ardhacodes.subs1_jetpack.ui.movie

import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity

interface CallbackMov {
    fun onItemClicked(movieEntity: MovieEntity)
}