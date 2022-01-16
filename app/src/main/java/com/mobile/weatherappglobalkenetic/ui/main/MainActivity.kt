package com.mobile.weatherappglobalkenetic.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.weatherappglobalkenetic.R
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val permissionsViewModel: PermissionsViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initNavController()
        observeViewModel()
    }

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
    private fun observeViewModel() {
        observePermissions()
        observerBackgroundColor()
    }
    private fun observePermissions() {
        permissionsViewModel.locationPermissionsRequested.observe(this) {
            if (it.contentIfNotHandled == true) {
                checkLocationPermissions()
            }
        }
    }
    private fun observerBackgroundColor() {
        weatherViewModel.backgroundColor.observe(this) {
            setBackgroundColor(it)
        }
    }

    private fun setBackgroundColor(it: Int?) {
        if (it != null) {
            val color = resources.getColor(it, null)
            binding.toolbar.setBackgroundColor(color)
            binding.parentContainer.setBackgroundColor(color)
        }
    }
    private fun checkLocationPermissions() {
        if (TrackingUtils.hasLocationPermissions(this)) {
            permissionsViewModel.permissionsGranted(true)
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.location_permissions_required),
            LOCATION_PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permissionsViewModel.permissionsGranted(false)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            checkLocationPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionsViewModel.permissionsGranted(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun getAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration.Builder(R.id.weatherFragment)
            .build()
    }
}