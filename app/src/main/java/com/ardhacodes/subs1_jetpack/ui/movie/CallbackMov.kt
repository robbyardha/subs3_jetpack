package com.ardhacodes.subs1_jetpack.ui.movie

import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity

interface CallbackMov {
    fun onItemClicked(movieEntity: MovieEntity)
}