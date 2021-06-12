package com.ardhacodes.subs1_jetpack.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
////    private var activityMainBinding: ActivityMainBinding? = null
////    private val binding get() = activityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
////        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(view)
//
//        configBottomNavigation()
//
//    }
//
//    public fun configBottomNavigation()
//    {
//        val navView = binding.mainNavigation
//        val navContainer = supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag) as NavHostFragment
//        NavigationUI.setupWithNavController(
//            navView,
//            navContainer.navController
//        )
//    }
//
//    fun TitleActionBar(title:String)
//    {
////        val actionBar = supportActionBar
////        actionBar?.title = "Home Theater List Movie FOX"
////        supportActionBar?.elevation = 0f
//        supportActionBar?.title = title
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        var activityMainBinding: ActivityMainBinding? = null
//        activityMainBinding = null
//
//    }
//    fun setActionBarTitle(title: String) {
//        supportActionBar?.title = title
//    }
private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupBottomNav()

    }

    private fun setupBottomNav() {
        val bottomNavigationView = binding?.bottomNavMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.navController
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

}

