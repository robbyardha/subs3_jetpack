package com.ardhacodes.subs1_jetpack.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.databinding.ActivityMainBinding
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val activityMainbinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(activityMainbinding.root)
//
//        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
//        activityMainbinding.viewPager.adapter = sectionsPagerAdapter
//        activityMainbinding.tabs.setupWithViewPager(activityMainbinding.viewPager)
//
//        TitleActionBar()
//        setupViewModel()
        setupToolbar()
        setupViewModel()
        setupNavigationController()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this@MainActivity,
            factory
        )[MainViewModel::class.java]
    }

    fun TitleActionBar() {
        val actionBar = supportActionBar
        actionBar?.title = "Home Theater List Movie FOX"
        supportActionBar?.elevation = 0f
    }


    //    private fun setupNavigationController() {
//        val navView: BottomNavigationView = findViewById(R.id.bottom_navbar)
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_movie,
//            R.id.navigation_tvshow,
//            R.id.navigation_favorite
//        ).build()
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }

    private fun setupToolbar() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.elevation = 0F
    }

    private fun setupNavigationController() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navbar)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_movie,
            R.id.navigation_tvshow,
            R.id.navigation_favorite
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}