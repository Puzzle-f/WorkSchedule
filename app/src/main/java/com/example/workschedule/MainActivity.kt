package com.example.workschedule

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Database
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.databinding.ActivityMainBinding
import com.example.workschedule.domain.saveFakeDataToDB
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main,
                R.id.nav_route_edit,
                R.id.nav_trains,
                R.id.nav_drivers,
                R.id.nav_train_edit,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fill_db_for_demonstration -> {
                val database: ScheduleDataBase by inject()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) { saveFakeDataToDB(database) }
                    Toast.makeText(
                        applicationContext,
                        "Данные в БД успешно добавлены",
                        Toast.LENGTH_LONG
                    ).show()
                    recreate()
                }
            }
            R.id.action_clear_db -> {
                val database: ScheduleDataBase by inject()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        clearDatabase(database)
                    }
                    Toast.makeText(
                        applicationContext,
                        "Данные из БД успешно удалены",
                        Toast.LENGTH_LONG
                    ).show()
                    recreate()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearDatabase(database: ScheduleDataBase) {
        database.runInTransaction {
            database.clearAllTables()
            database.openHelper.writableDatabase.execSQL("DELETE FROM sqlite_sequence")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
