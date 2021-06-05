package com.ardhacodes.subs1_jetpack.buildermodule.main

import com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite.MovieFavFragment
import com.ardhacodes.subs1_jetpack.ui.favorite.tvfavorite.TvFavFragment
import dagger.android.ContributesAndroidInjector

abstract class FavoriteFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment() : MovieFavFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteTvFragment() : TvFavFragment
}