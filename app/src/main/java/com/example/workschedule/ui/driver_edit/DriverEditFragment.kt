package com.example.workschedule.ui.driver_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
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

    internal data class EditTextValidation(
        var validPersonnelNumber: Boolean = false,
        var validSurname: Boolean = false,
        var validName: Boolean = false,
        var validPatronymic: Boolean = false
    )

    private val editTextValidation = EditTextValidation()

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DRIVER_ID)
    }

    override fun initView() = with(binding) {
        editDriverFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() = with(binding) {
        driverEditFragmentPersonnelNumber.addTextChangedListener { text ->
            editTextValidation.validPersonnelNumber = !text.isNullOrBlank() && text.isDigitsOnly()
            checkSaveButtonEnable()
        }
        driverEditFragmentSurname.addTextChangedListener { text ->
            editTextValidation.validSurname = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        driverEditFragmentName.addTextChangedListener { text ->
            editTextValidation.validName = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        driverEditFragmentPatronymic.addTextChangedListener { text ->
            editTextValidation.validPatronymic = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        driverEditFragmentSaveButton.setOnClickListener {
            driverEditViewModel.saveDriver(
                Driver(
                    driverId ?: 0,
                    driverEditFragmentPersonnelNumber.text.toString().toInt(),
                    driverEditFragmentSurname.text.toString(),
                    driverEditFragmentName.text.toString(),
                    driverEditFragmentPatronymic.text.toString(),
                    0,
                    0,
                    adapter.getAccessList()
                )
            )
            Toast.makeText(
                activity, getString(R.string.driverEditDataInputSuccess), Toast.LENGTH_LONG
            ).show()
            it.findNavController().navigateUp()
        }
        driverEditFragmentCancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun checkSaveButtonEnable() = with(editTextValidation) {
        binding.driverEditFragmentSaveButton.isEnabled =
            validPersonnelNumber && validSurname && validName && validPatronymic
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