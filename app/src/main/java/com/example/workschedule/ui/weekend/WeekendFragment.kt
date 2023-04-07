package com.example.workschedule.ui.weekend

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.workschedule.databinding.FragmentWeekendBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment
import org.koin.android.viewmodel.ext.android.viewModel

class WeekendFragment:
BaseFragment<FragmentWeekendBinding>(FragmentWeekendBinding::inflate){

    private val weekendViewModel: WeekendViewModel by viewModel()
    private var driverId: Int? = null

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DriverEditFragment.DRIVER_ID)
    }

    override fun initView() {

    }

    override fun initListeners() {
        with(binding){
            driverEditFragmentCancelButton.setOnClickListener {
                it.findNavController().navigateUp()
            }
        }
    }

    override fun initObservers() {

    }


}