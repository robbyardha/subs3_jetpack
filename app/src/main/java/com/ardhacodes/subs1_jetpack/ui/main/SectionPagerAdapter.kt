package com.ardhacodes.subs1_jetpack.ui.main

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.ui.movie.MovieFragment
import com.ardhacodes.subs1_jetpack.ui.tv.TvFragment

class SectionPagerAdapter(val mContext: Context, fragmng : FragmentManager) : FragmentPagerAdapter(fragmng, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object{
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.frag_mov, R.string.frag_tv)
    }
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment =
        when (position){
            0 -> MovieFragment()
            1 -> TvFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLE[position])
}