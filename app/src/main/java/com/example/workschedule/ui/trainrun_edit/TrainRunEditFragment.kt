package com.example.workschedule.ui.trainrun_edit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainrunEditBinding
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.utils.*
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
    private val directionsAdapter by lazy {
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
    var isEditManually = false
    var countNight = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "Добавление", Toast.LENGTH_SHORT).show()
    }

    private var trainRunId: Int? = null
    private var trainsList: List<Direction> = mutableListOf()
    private var driversList: List<Driver> = mutableListOf()
    private var trainPeriodicity = TrainPeriodicity.SINGLE

    internal data class EditTextValidation(
        var validTrainNumber: Boolean = false,
        var validTrainDirection: Boolean = false,
        var validTrainPeriodicity: Boolean = false,
        var validDateTime: Boolean = false,
        var validTravelTime: Boolean = false,
        var validWorkTime: Boolean = false
//        var validDriverFIO: Boolean = false,
//        var validRestTime: Boolean = false,

    )

    private val editTextValidation = EditTextValidation()

    override fun readArguments(bundle: Bundle) {
        trainRunId = bundle.getInt(TRAIN_RUN_ID)
    }

    override fun initView() = with(binding) {
        routeEditFragmentDriver.setAdapter(driversAdapter)
        routeEditFragmentTrainDirection.setAdapter(directionsAdapter)
        routeEditFragmentPeriodicity.setAdapter(periodicityAdapter)
        routeEditFragmentPeriodicity.setText(
            periodicityAdapter.getItem(0).toString(),
            false
        )
        editTextValidation.validTrainPeriodicity = true
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

    @SuppressLint("SetTextI18n")
    private val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        val text = (v as EditText).text
        if (!hasFocus && !text.isNullOrBlank() && !text.contains(":")) {
            v.setText("$text:00")
        }
    }

    override fun initListeners() = with(binding) {
        routeEditFragmentDateTime.setOnClickListener {
            pickDateTime(
                if (routeEditFragmentDateTime.text!!.isNotBlank())
                    LocalDateTime.parse(
                        routeEditFragmentDateTime.text,
                        DateTimeFormatter.ofPattern("dd.MM.y HH:mm")
                    )
                else
                    LocalDateTime.now()
            )
        }
        routeEditFragmentDateTime.addTextChangedListener { text ->
            editTextValidation.validDateTime = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        routeEditFragmentTrainNumber.addTextChangedListener { text ->
            editTextValidation.validTrainNumber = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        routeEditFragmentTrainDirection.addTextChangedListener { text ->
            if (!text.isNullOrBlank()) {
                val directionId = trainsList.find { it.name == text.toString() }?.id
                driversAdapter.clear()
                driversAdapter.add(getString(R.string.edit_periodicity_default_item))
                driversAdapter.addAll(driversList
//                    .filter { directionId in it.accessTrainsId }
                    .map { it.FIO })
                driversAdapter.notifyDataSetChanged()
                routeEditFragmentDriver.setText(
                    driversAdapter.getItem(0).toString(),
                    false
                )
            }
            editTextValidation.validTrainDirection = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        routeEditFragmentPeriodicity.addTextChangedListener { text ->
            editTextValidation.validTrainPeriodicity = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
//        routeEditFragmentDriver.addTextChangedListener { text ->
//            editTextValidation.validDriverFIO = !text.isNullOrBlank()
//            checkSaveButtonEnable()
//        }
        routeEditFragmentTimeTo.addTextChangedListener { text ->
            editTextValidation.validTravelTime = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }

        routeEditFragmentTimeTo.onFocusChangeListener = focusChangeListener
        routeEditFragmentPeriodicity.setOnItemClickListener { _, _, position, _ ->
            trainPeriodicity = position.toPeriodicity
        }

        workTimeEditText.onFocusChangeListener = focusChangeListener

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                -1 -> countNight = 0
                radioButton1.id -> countNight = 0
                radioButton2.id -> countNight = 1
                radioButton3.id -> countNight = 2
            }
        }

        routeEditFragmentSaveButton.setOnClickListener {
            routeEditFragmentTimeTo.clearFocus()
            val direction = routeEditFragmentTrainDirection.text.toString()
            val directionId = trainsList.find { it.name == direction }?.id ?: 0
            val trainNumber = routeEditFragmentTrainNumber.text.toString().toInt()
            val driverNameText = routeEditFragmentDriver.text.toString()
//            val driverName = if (driverNameText != driversAdapter.getItem(0)){
//                isEditManually = true
//                driverNameText
//            }  else ""
            val driverId = driversList.find { it.FIO == driverNameText }?.id ?: 0
            val startTime = LocalDateTime.parse(
                routeEditFragmentDateTime.text,
                DateTimeFormatter.ofPattern("dd.MM.y HH:mm")
            ).toLong()
            val travelTime = routeEditFragmentTimeTo.text.toString().timeToMillis
            val workTime = workTimeEditText.text.toString().timeToMillis
            val note = if (noteEditText.text == null) "" else noteEditText.text.toString()
            isEditManually = driverId != 0

            trainRunEditViewModel.saveTrainRun(
                TrainRun(
                    trainRunId ?: 0,
                    trainNumber.toString(),
                    driverId,
                    directionId,
                    startTime,
                    travelTime,
                    countNight,
                    workTime,
                    trainPeriodicity,
                    isEditManually,
                    note
                )
            )
            findNavController().navigateUp()
        }
        routeEditFragmentCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkSaveButtonEnable() = with(editTextValidation) {
        binding.routeEditFragmentSaveButton.isEnabled =
            validDateTime && validTrainNumber && validTrainDirection && validTrainPeriodicity && validTravelTime
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
                .directions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    trainsList = list
                    directionsAdapter.clear()
                    directionsAdapter.addAll(list.map { it.name })
                    directionsAdapter.notifyDataSetChanged()
                }
        }
        lifecycleScope.launchWhenStarted {
            trainRunEditViewModel
                .drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    driversList = list
                    driversAdapter.clear()
                    driversAdapter.add(getString(R.string.edit_periodicity_default_item))
                    driversAdapter.addAll(list.map { it.FIO })
                    driversAdapter.notifyDataSetChanged()
                    binding.routeEditFragmentDriver.setText(
                        driversAdapter.getItem(0).toString(),
                        false
                    )
                }
        }
        trainRunEditViewModel.getDrivers()
        trainRunEditViewModel.getDirections()
    }

    private fun renderData(trainRun: TrainRun) = with(binding) {
        routeEditFragmentDateTime.setText(
            trainRun.startTime.toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd.MM.y HH:mm"))
        )
//        routeEditFragmentTrainNumber.setText(trainRun.trainNumber.toString())
//        routeEditFragmentTrainDirection.setText(trainRun.trainDirection, false)
//        trainPeriodicity = trainRun.trainPeriodicity
//        routeEditFragmentPeriodicity.setText(
//            periodicityAdapter.getItem(trainPeriodicity.toInt).toString(), false
//        )
//        routeEditFragmentDriver.setText(trainRun.driverName, false)
//        routeEditFragmentTimeTo.setText(trainRun.travelTime.toTimeString)
//        routeEditFragmentTimeRest.setText(trainRun.travelRestTime.toTimeString)
//        routeEditFragmentTimeFrom.setText(trainRun.backTravelTime.toTimeString)
    }

    companion object {
        const val TRAIN_RUN_ID = "train_run_id"
    }
}
