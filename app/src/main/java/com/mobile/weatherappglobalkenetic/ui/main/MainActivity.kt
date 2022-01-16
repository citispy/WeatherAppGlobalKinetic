package com.mobile.weatherappglobalkenetic.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.weatherappglobalkenetic.R
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavController()
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.editCity) {
            navController.navigate(R.id.action_weatherFragment_to_placePickerFragment)
        } else if ( item.itemId == R.id.refresh) {
            permissionsViewModel.requestLocationPermissions(true)
        }

        return true
    }
    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost)
                as NavHostFragment
        navController = navHostFragment.navController

        navController.setGraph(R.navigation.nav_graph)
        //Add destination change listener for cases when title doesn't set
        navController.addOnDestinationChangedListener {_, destination, _ ->
            if (destination.id == R.id.weatherFragment) {
                binding.toolbar.title = SharedPrefsUtils(this).getPrefs(KEY_CITY_NAME)
            }}

        //Toolbar
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.toolbar, navController, getAppBarConfiguration())
    }
    }
}