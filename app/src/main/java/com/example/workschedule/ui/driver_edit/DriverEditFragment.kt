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
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            lifecycleScope.launch {

                val driverLocal = Driver(
                    driverId ?: 0,
                    driverEditFragmentPersonnelNumber.text.toString().toInt(),
                    driverEditFragmentSurname.text.toString(),
                    driverEditFragmentName.text.toString(),
                    driverEditFragmentPatronymic.text.toString()
                )
                if (driverLocal.id == 0) {
                    lifecycleScope.launch {
                        driverEditViewModel.saveDriver(driverLocal).join()
                        driverEditViewModel.getDriverByPersonalNumberAndSurname(
                            driverLocal.personalNumber,
                            driverLocal.surname
                        )
                        driverEditViewModel.savePermissions(
                            driverEditViewModel.newDriver.value?.id,
                            adapter.permissionListFromAdapter
                        )
                    }
                } else {
                    driverEditViewModel.updateDriver(driverLocal)
                    driverEditViewModel.savePermissions(
                        driverLocal.id,
                        adapter.permissionListFromAdapter
                    ).join()
                delay(100)
                }
                Toast.makeText(
                    activity,
                    "машинист " +
                            "${driverEditFragmentSurname.text} " + "успешно добавлен",
                    Toast.LENGTH_LONG
                ).show()
                it.findNavController().navigateUp()
            }

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
            driverEditViewModel.getPermissions(it)
            driverEditViewModel.getDriver(it)
            lifecycleScope.launchWhenStarted {
                driverEditViewModel.driver
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { driver ->
                        driver?.let {
                            renderDataDriver(driver)
                        }
                    }
            }
        }
        lifecycleScope.launchWhenStarted {
            driverEditViewModel.directions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        lifecycleScope.launchWhenStarted {
            driverEditViewModel.permissions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { permissions ->
                    adapter.permissionListFromAdapter.addAll(permissions.map { it.idDirection })
                    renderDataPermission()
                }
        }
        driverEditViewModel.getDirections()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderDataDriver(driver: Driver) = with(binding) {
        driverEditFragmentPersonnelNumber.setText("${driver.personalNumber}")
        driverEditFragmentSurname.setText(driver.surname)
        driverEditFragmentName.setText(driver.name)
        driverEditFragmentPatronymic.setText(driver.patronymic)
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderDataPermission() {
        adapter.permissionList()
        adapter.notifyDataSetChanged()
    }


    companion object {
        const val DRIVER_ID = "driver_id"
    }
}