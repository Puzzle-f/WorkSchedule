package com.example.workschedule.ui.driver_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.domain.models.Driver
import org.koin.android.viewmodel.ext.android.viewModel

class DriverEditFragment : Fragment() {

    private val driverEditViewModel: DriverEditViewModel by viewModel()
    private var _binding: FragmentDriverEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDriverEditBinding? = null")
    private val adapter: DriverEditAdapter by lazy { DriverEditAdapter() }
    private var driverId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        arguments?.let {
            driverId = it.getInt(DRIVER_ID)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initButtons()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() = with(binding) {
        editDriverFragmentRecyclerView.adapter = adapter
        driverId?.let {
            lifecycleScope.launchWhenStarted {
                driverEditViewModel.driver
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { driver ->
                        driver?.let {
                            driverEditFragmentId.setText("${driver.id}")
                            driverEditFragmentSurname.setText(driver.surname)
                            driverEditFragmentName.setText(driver.name)
                            driverEditFragmentPatronymic.setText(driver.patronymic)
                            adapter.setAccessList(driver.accessTrainsId)
                            adapter.notifyDataSetChanged()
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

    private fun initButtons() = with(binding) {
        driverEditFragmentCancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
        driverEditFragmentSaveButton.setOnClickListener {
            if (driverEditFragmentId.text.toString().isNotBlank() &&
                driverEditFragmentSurname.text.toString().isNotBlank() &&
                driverEditFragmentName.text.toString().isNotBlank() &&
                driverEditFragmentPatronymic.text.toString().isNotBlank()
            ) {
                driverEditViewModel.saveDriver(
                    Driver(
                        driverEditFragmentId.text.toString().toInt(),
                        driverEditFragmentSurname.text.toString(),
                        driverEditFragmentName.text.toString(),
                        driverEditFragmentPatronymic.text.toString(),
                        0,
                        0,
                        adapter.getAccessList()
                    )
                )
                val driverFIO = "${driverEditFragmentSurname.text} " +
                        "${driverEditFragmentName.text?.first()}. " +
                        "${driverEditFragmentPatronymic.text?.first()}"
                Toast.makeText(
                    activity,
                    getString(R.string.driverEditDataInputSuccess),
                    Toast.LENGTH_LONG
                ).show()
                it.findNavController().navigateUp()
            } else
                Toast.makeText(
                    activity, getString(R.string.driverEditDataInputIncorrect),
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DRIVER_ID = "driver_id"
    }
}