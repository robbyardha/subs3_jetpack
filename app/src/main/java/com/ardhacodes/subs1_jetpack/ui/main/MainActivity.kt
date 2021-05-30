package com.ardhacodes.subs1_jetpack.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainbinding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        activityMainbinding.viewPager.adapter = sectionsPagerAdapter
        activityMainbinding.tabs.setupWithViewPager(activityMainbinding.viewPager)

        TitleActionBar()

    }

    fun TitleActionBar()
    {
        val actionBar = supportActionBar
        actionBar?.title = "Home Theater List Movie FOX"
        supportActionBar?.elevation = 0f
    }
}