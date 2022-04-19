package com.virtual.arrownews

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.virtual.arrownews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding : ActivityMainBinding
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, mainActivityBinding.root, R.string.open_drawer, R.string.open_drawer)
        mainActivityBinding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navHost = supportFragmentManager.findFragmentById(R.id.fragments_container) as NavHostFragment
        navController = navHost.findNavController()

        mainActivityBinding.bottomNavView.setupWithNavController(navController)

        //val appbarConfig = AppBarConfiguration(navGraph = navController.graph, drawerLayout = mainActivityBinding.root)
        //setupActionBarWithNavController(navController = navController, drawerLayout = mainActivityBinding.root)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            title = destination.label
        }

        mainActivityBinding.swipeAndRefresh.setOnRefreshListener {

            //val currentScreen = navController.currentDestination?.id
            val currentScreen = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.primaryNavigationFragment
            if (currentScreen != null && currentScreen is RefreshAdapter)
                currentScreen.refreshAdapter()

            mainActivityBinding.swipeAndRefresh.isRefreshing = false
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fragments_container).navigateUp()

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return actionBarDrawerToggle.onOptionsItemSelected(item)
    }
}

interface RefreshAdapter{
    fun refreshAdapter()
}