package com.ardhacodes.subs1_jetpack.ui.main

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.ui.favorite.FavoriteFragment
import com.ardhacodes.subs1_jetpack.ui.movie.MovieFragment
import com.ardhacodes.subs1_jetpack.ui.tv.TvFragment

class SectionPagerAdapter(private val fragmentAsList: List<Fragment>, fragManeger: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragManeger, lifecycle) {
    override fun getItemCount(): Int {
        return fragmentAsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentAsList[position]
    }
}
