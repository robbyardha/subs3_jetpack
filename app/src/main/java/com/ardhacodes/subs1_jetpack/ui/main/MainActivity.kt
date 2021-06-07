package com.ardhacodes.subs1_jetpack.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.databinding.ActivityMainBinding
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
//    private val binding get() = activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainbinding.root)
        setupBottomNav()
//        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
//        activityMainbinding.viewPager.adapter = sectionsPagerAdapter
//        activityMainbinding.tabs.setupWithViewPager(activityMainbinding.viewPager)
//
//        TitleActionBar()

    }

    private fun setupBottomNav() {
        val bottomNavigationView = activityMainBinding?.bottomNavbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.navController
            )
        }
    }

    fun TitleActionBar()
    {
        val actionBar = supportActionBar
        actionBar?.title = "Home Theater List Movie FOX"
        supportActionBar?.elevation = 0f
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}