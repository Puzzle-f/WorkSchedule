package com.example.workschedule.ui.trainrun_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainrunEditBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.utils.FIO
import com.example.workschedule.utils.timeToMillis
import com.example.workschedule.utils.toTimeString
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrainRunEditFragment :
    BaseFragment<FragmentTrainrunEditBinding>(FragmentTrainrunEditBinding::inflate) {
    private val trainRunEditViewModel: TrainRunEditViewModel by viewModel()
    private val driversAdapter by lazy {
        ArrayAdapter<String>(
            requireActivity(),
            R.layout.fragment_trainrun_edit_dropdown_list_item,
            mutableListOf()
        )
    }
    private val trainsAdapter by lazy {
        ArrayAdapter<String>(
            requireActivity(),
            R.layout.fragment_trainrun_edit_dropdown_list_item,
            mutableListOf()
        )
    }
    private val periodicityAdapter by lazy {
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.periodicity,
            R.layout.fragment_trainrun_edit_dropdown_list_item
        )
    }
    private var trainRunId: Int? = null
    private var trainsList: List<Train> = mutableListOf()
    private var driversList: List<Driver> = mutableListOf()

    override fun readArguments(bundle: Bundle) {
        trainRunId = bundle.getInt(TRAIN_RUN_ID)
    }

    override fun initView() {
        binding.routeEditFragmentDriver.setAdapter(driversAdapter)
        binding.routeEditFragmentTrainDirection.setAdapter(trainsAdapter)
        binding.routeEditFragmentPeriodicity.setAdapter(periodicityAdapter)
    }

    private fun pickDateTime(time: LocalDateTime) {
        val startYear = time.year
        val startMonth = time.month.value - 1
        val startDay = time.dayOfMonth
        val startHour = time.hour
        val startMinute = time.minute

        DatePickerDialog(requireContext(), { _, year, month, day ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                val pickedDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                binding.routeEditFragmentDateTime.setText(
                    pickedDateTime.format(DateTimeFormatter.ofPattern("dd.MM.y HH:mm"))
                )
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    override fun initListeners() {
        binding.routeEditFragmentDateTime.setOnClickListener {
            pickDateTime(
                if (binding.routeEditFragmentDateTime.text!!.isNotBlank())
                    LocalDateTime.parse(
                        binding.routeEditFragmentDateTime.text,
                        DateTimeFormatter.ofPattern("dd.MM.y HH:mm")
                    )
                else
                    LocalDateTime.now()
            )
        }
        binding.routeEditFragmentSaveButton.setOnClickListener {
            val trainDirection = binding.routeEditFragmentTrainDirection.text.toString()
            val trainId = trainsList.find { it.direction == trainDirection }?.id ?: 0
            val trainNumber = binding.routeEditFragmentTrainNumber.text.toString().toInt()
            val driverName = binding.routeEditFragmentDriver.text.toString()
            val driverId = driversList.find { it.FIO == driverName }?.id ?: 0
            val startTime = LocalDateTime.parse(
                binding.routeEditFragmentDateTime.text,
                DateTimeFormatter.ofPattern("dd.MM.y HH:mm")
            )
            val travelTime = binding.routeEditFragmentTimeTo.text.toString().timeToMillis
            val restTime = binding.routeEditFragmentTimeRest.text.toString().timeToMillis
            val backTravelTime = binding.routeEditFragmentTimeFrom.text.toString().timeToMillis
            trainRunEditViewModel.saveTrainRun(
                TrainRun(
                    trainRunId ?: 0,
                    trainId,
                    trainNumber,
                    trainDirection,
                    driverId,
                    driverName,
                    startTime,
                    travelTime,
                    restTime,
                    backTravelTime
                )
            )
            findNavController().navigateUp()
        }
        binding.routeEditFragmentCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initObservers() {
        trainRunId?.let { trainRunId ->
            lifecycleScope.launchWhenStarted {
                trainRunEditViewModel
                    .trainRun
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        it?.let { renderData(it) }
                    }
            }
            trainRunEditViewModel.getTrainRun(trainRunId)
        }
        lifecycleScope.launchWhenStarted {
            trainRunEditViewModel
                .trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    trainsList = list
                    trainsAdapter.clear()
                    trainsAdapter.addAll(list.map { it.direction })
                    trainsAdapter.notifyDataSetChanged()
                }
        }
        lifecycleScope.launchWhenStarted {
            trainRunEditViewModel
                .drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    driversList = list
                    driversAdapter.clear()
                    driversAdapter.addAll(list.map { it.FIO })
                    driversAdapter.notifyDataSetChanged()
                }
        }
        trainRunEditViewModel.getDrivers()
        trainRunEditViewModel.getTrains()
    }

    private fun renderData(trainRun: TrainRun) = with(binding) {
        routeEditFragmentDateTime.setText(
            trainRun.startTime.format(DateTimeFormatter.ofPattern("dd.MM.y HH:mm"))
        )
        routeEditFragmentTrainNumber.setText(trainRun.trainNumber.toString())
        routeEditFragmentTrainDirection.setText(trainRun.trainDirection, false)
        routeEditFragmentDriver.setText(trainRun.driverName, false)
        routeEditFragmentPeriodicity.setText("")
        routeEditFragmentTimeTo.setText(trainRun.travelTime.toTimeString)
        routeEditFragmentTimeRest.setText(trainRun.travelRestTime.toTimeString)
        routeEditFragmentTimeFrom.setText(trainRun.backTravelTime.toTimeString)
    }

    companion object {
        const val TRAIN_RUN_ID = "train_run_id"
    }
}
