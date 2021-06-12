package com.ardhacodes.subs1_jetpack.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    private val binding get() = activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        configBottomNavigation()

    }

    public fun configBottomNavigation()
    {
        val navView =binding?.MainNavigation
        val navContainer = supportFragmentManager.findFragmentById(R.id.FragmentContainerView) as NavHostFragment
        if (navView != null){
            NavigationUI.setupWithNavController(
                navView,
                navContainer.navController
            )
        }
    }

    fun TitleActionBar(title:String)
    {
//        val actionBar = supportActionBar
//        actionBar?.title = "Home Theater List Movie FOX"
//        supportActionBar?.elevation = 0f
        supportActionBar?.title = title
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMainBinding = null
    }
    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

}