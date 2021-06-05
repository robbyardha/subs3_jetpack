package com.ardhacodes.subs1_jetpack.buildermodule.main

import com.ardhacodes.subs1_jetpack.ui.favorite.FavoriteFragment
import com.ardhacodes.subs1_jetpack.ui.movie.MovieFragment
import com.ardhacodes.subs1_jetpack.ui.tv.TvFragment
import dagger.android.ContributesAndroidInjector

abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMovieFragment() : MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeTvFragment() : TvFragment

    @ContributesAndroidInjector(modules = [FavoriteFragmentBuildersModule::class])
    abstract fun contributeFavoriteFragment() : FavoriteFragment
}