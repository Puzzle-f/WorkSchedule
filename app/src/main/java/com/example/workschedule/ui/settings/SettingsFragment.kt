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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonNewTrain = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_train)
        super.onViewCreated(view, savedInstanceState)
    }

    var sharedPreferences: SharedPreferences? = null
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
}

 val nightPeriod = LocalTime.of(0,0)..LocalTime.of(5, 59)

const val MIN_REST = 16L
var ignoreWeekends = true
private fun Int.dayToMillis() = this*24*3600*1000
private val PLANNING_HORIZON_LOCAL = 3
val PLANNING_HORIZON = PLANNING_HORIZON_LOCAL.dayToMillis()