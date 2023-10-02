package com.example.workschedule.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentSettingsBinding
import com.example.workschedule.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton
import java.time.LocalTime

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private lateinit var buttonNewTrain: MaterialButton
    var sharedPreferences: SharedPreferences? = null

    val NAME_SETTINGS = "my settings"
    val HORIZON_PLANING = "horizon planing"
    val HORIZON_PLANNING_COMMON = "horizon planing common"
    val MIN_REST = "min rest"
    val NIGHT_PERIOD = "night period"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonNewTrain = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_train)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun readArguments(bundle: Bundle) {
    }

    override fun initView() {

    }

    override fun initListeners() {

    }

    override fun initObservers() {

    }

    override fun onStart() {
        buttonNewTrain.visibility = View.INVISIBLE
        super.onStart()
    }

    fun saveDefaultSettings(){



    }

}

 val nightPeriod = LocalTime.of(0,0)..LocalTime.of(6, 0)
const val MIN_REST = 16L
var ignoreWeekends = true
private fun Int.dayToMillis() = this*24*3600*1000
private val PLANNING_HORIZON_LOCAL = 1
val PLANNING_HORIZON = PLANNING_HORIZON_LOCAL.dayToMillis()
val PLANNING_HORIZON_COMMON = (PLANNING_HORIZON_LOCAL + 2).dayToMillis()
const val CHECK_WEEKENDS = true