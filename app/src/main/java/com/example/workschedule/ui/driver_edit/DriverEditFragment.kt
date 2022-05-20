package com.example.workschedule.ui.driver_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class DriverEditFragment :
    BaseFragment<FragmentDriverEditBinding>(FragmentDriverEditBinding::inflate) {

    private val driverEditViewModel: DriverEditViewModel by viewModel()
    private val adapter: DriverEditAdapter by lazy { DriverEditAdapter() }
    private var driverId: Int? = null

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DRIVER_ID)
    }

    override fun initView() = with(binding) {
        editDriverFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() = with(binding) {
        driverEditFragmentCancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
        driverEditFragmentSaveButton.setOnClickListener {
            val screenData = Driver(
                0,
                driverEditFragmentPersonnelNumber.text.toString().toIntOrNull(),
                driverEditFragmentSurname.text.toString(),
                driverEditFragmentName.text.toString(),
                driverEditFragmentPatronymic.text.toString(),
                0,
                0,
                adapter.getAccessList()
            )
            if (screenData.personnelNumber != null &&
                screenData.surname.isNotBlank() &&
                screenData.name.isNotBlank() &&
                screenData.patronymic.isNotBlank()
            ) {
                driverEditViewModel.saveDriver(screenData)
                Toast.makeText(
                    activity, getString(R.string.driverEditDataInputSuccess), Toast.LENGTH_LONG
                ).show()
                it.findNavController().navigateUp()
            } else
                Toast.makeText(
                    activity, getString(R.string.driverEditDataInputIncorrect), Toast.LENGTH_LONG
                ).show()
        }
    }

    override fun initObservers() {
        driverId?.let {
            lifecycleScope.launchWhenStarted {
                driverEditViewModel.driver
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { driver ->
                        driver?.let {
                            renderData(driver)
                        }
                    }
            }
            driverEditViewModel.getDriver(it)
        }
        lifecycleScope.launchWhenStarted {
            driverEditViewModel.trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        driverEditViewModel.getTrains()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderData(driver: Driver) = with(binding) {
        driverEditFragmentPersonnelNumber.setText("${driver.personnelNumber}")
        driverEditFragmentSurname.setText(driver.surname)
        driverEditFragmentName.setText(driver.name)
        driverEditFragmentPatronymic.setText(driver.patronymic)
        adapter.setAccessList(driver.accessTrainsId)
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val DRIVER_ID = "driver_id"
    }
}