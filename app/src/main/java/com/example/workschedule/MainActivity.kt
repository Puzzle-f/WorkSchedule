package com.example.workschedule

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.databinding.ActivityMainBinding
import com.example.workschedule.domain.clearDatabase
import com.example.workschedule.domain.saveFakeDataToDB
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
    private val navController = navHostFragment.navController
    val database: ScheduleDataBase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_main, R.id.nav_drivers, R.id.nav_trains), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fill_db_for_demonstration -> {
                actionWithDatabase(R.string.dataAdded) { saveFakeDataToDB(it) }
            }
            R.id.action_clear_db -> {
                actionWithDatabase(R.string.dataDeleted) { clearDatabase(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionWithDatabase(
        toastedStringId: Int,
        action: suspend (ScheduleDataBase) -> Unit
    ) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                action(database)
            }
            Toast.makeText(
                applicationContext, getString(toastedStringId), Toast.LENGTH_LONG
            ).show()
            navController.popBackStack(R.id.nav_main, true)
            navController.navigate(R.id.nav_main)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
