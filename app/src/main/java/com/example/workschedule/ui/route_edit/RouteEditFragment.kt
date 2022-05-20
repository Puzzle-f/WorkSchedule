package com.example.workschedule.ui.route_edit

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentRouteEditBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.drivers.DriversFragmentAdapter
import com.example.workschedule.ui.trains.TrainsFragmentAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class RouteEditFragment :
    BaseFragment<FragmentRouteEditBinding>(FragmentRouteEditBinding::inflate) {
    private val routeEditViewModel: RouteEditViewModel by viewModel()
    private val driversAdapter: DriversFragmentAdapter by lazy {
        DriversFragmentAdapter(requireActivity().menuInflater)
    }
    private val trainsAdapter: TrainsFragmentAdapter by lazy {
        TrainsFragmentAdapter(requireActivity().menuInflater)
    }
    private var trainRunId: Int? = null
    private var disembarkDateTimeTag: Int = 0
    private var trainsListTag: Int = 0
    private var driversListTag: Int = 0
    private var periodicityListTag: Int = 0
    private var timeToTag: Int = 0
    private var restTag: Int = 0
    private var timeFromTag: Int = 0
    private val defaultConstraints: ConstraintSet = ConstraintSet()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultConstraints.clone(binding.routeEditConstraints)
        initView()
    }

    override fun readArguments(bundle: Bundle) {
        trainRunId = bundle.getInt(TRAIN_RUN_ID)
    }

    override fun initView() {
        binding.routeEditFragmentDriversRecycler.adapter = driversAdapter
        binding.routeEditFragmentTrainsRecycler.adapter = trainsAdapter
        setDefaultTimeAndDate()
        initSetButtons()
    }


    override fun initListeners() {
        binding.routeEditFragmentDateTime.setOnClickListener {
            when (disembarkDateTimeTag) {
                0 -> {
                    disembarkDateTimeTag = 1
                    hideSaveCancelButtons()
                    hidePickers()
                    with(binding) {
                        routeEditFragmentTimeFromLayout.visibility = View.GONE
                        routeEditFragmentTimeRestLayout.visibility = View.GONE
                        routeEditFragmentTimeToLayout.visibility = View.GONE
                        routeEditFragmentPeriodicityLayout.visibility = View.GONE
                        routeEditFragmentDriverLayout.visibility = View.GONE
                        routeEditFragmentTrainNumberLayout.visibility = View.GONE
                        routeEditFragmentDisembarkSetButton.visibility = View.VISIBLE
                        routeEditFragmentDisembarkDateTimePickerLayout.visibility = View.VISIBLE
                    }
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentTrainNumber.setOnClickListener {
            when (trainsListTag) {
                0 -> {
                    trainsListTag = 1
                    with(binding) {
                        routeEditFragmentTrainNumberLayout.endIconDrawable =
                            getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                        routeEditFragmentDriverLayout
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                topToBottom = routeEditFragmentGuidelineRecycler.id
                            }
                        routeEditFragmentTimeFromLayout.visibility = View.GONE
                        routeEditFragmentTimeRestLayout.visibility = View.GONE
                        routeEditFragmentTimeToLayout.visibility = View.GONE
                        routeEditFragmentPeriodicityLayout.visibility = View.GONE
                        routeEditFragmentDriverLayout.visibility = View.GONE
                    }
                    hidePickers()
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentDriver.setOnClickListener {
            when (driversListTag) {
                0 -> {
                    driversListTag = 1
                    binding.routeEditFragmentDriverLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentPeriodicityLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                        }
                    binding.routeEditFragmentTimeFromLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeToLayout.visibility = View.GONE
                    binding.routeEditFragmentPeriodicityLayout.visibility = View.GONE
                    hidePickers()
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentPeriodicity.setOnClickListener {
            when (periodicityListTag) {
                0 -> {
                    periodicityListTag = 1
                    binding.routeEditFragmentPeriodicityLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentTimeToLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                        }
                    binding.routeEditFragmentTimeFromLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeToLayout.visibility = View.GONE
                    hidePickers()
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentTimeTo.setOnClickListener {
            when (timeToTag) {
                0 -> {
                    timeToTag = 1
                    hideSaveCancelButtons()
                    hidePickers()
                    with(binding) {
                        routeEditFragmentTimeRestLayout.visibility = View.GONE
                        routeEditFragmentTimeFromLayout.visibility = View.GONE
                        routeEditFragmentTimeToSetButton.visibility = View.VISIBLE
                        routeEditTimePickerTimeTo.visibility = View.VISIBLE
                    }
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentTimeRest.setOnClickListener {
            when (restTag) {
                0 -> {
                    restTag = 1
                    hideSaveCancelButtons()
                    hidePickers()
                    with(binding) {
                        routeEditFragmentTimeRestLayout
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                topToBottom = binding.routeEditFragmentPeriodicityLayout.id
                            }
                        routeEditFragmentTimeToLayout.visibility = View.GONE
                        routeEditFragmentTimeFromLayout.visibility = View.GONE
                        routeEditFragmentRestSetButton.visibility = View.VISIBLE
                        routeEditTimePickerTimeRest.visibility = View.VISIBLE
                    }
                }
                1 -> setDefaultConstraints()
            }
        }

        binding.routeEditFragmentTimeFrom.setOnClickListener {
            when (timeFromTag) {
                0 -> {
                    timeFromTag = 1
                    hidePickers()
                    hideSaveCancelButtons()
                    with(binding) {
                        routeEditFragmentTimeFromLayout
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                topToBottom = binding.routeEditFragmentPeriodicityLayout.id
                            }
                        routeEditTimePickerTimeTo.visibility = View.GONE
                        routeEditFragmentTimeRestLayout.visibility = View.GONE
                        routeEditFragmentTimeFromSetButton.visibility = View.VISIBLE
                        routeEditTimePickerTimeFrom.visibility = View.VISIBLE
                    }
                }
                1 -> setDefaultConstraints()
            }
        }
    }

    override fun initObservers() {
        trainRunId?.let {

        }
        lifecycleScope.launchWhenStarted {
            routeEditViewModel
                .trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    trainsAdapter.submitList(it)
                }
        }
        lifecycleScope.launchWhenStarted {
            routeEditViewModel
                .drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    driversAdapter.submitList(it)
                }
        }
        routeEditViewModel.getDrivers()
        routeEditViewModel.getTrains()
    }

    private fun setDefaultTimeAndDate() {
        with(binding) {
            routeFragmentEditDisembarkTimePicker.apply {
                setIs24HourView(true)
                hour = 0
                minute = 0
            }
            routeEditTimePickerTimeTo.apply {
                setIs24HourView(true)
                hour = 0
                minute = 0
            }
            routeEditTimePickerTimeRest.apply {
                setIs24HourView(true)
                hour = 0
                minute = 0
            }
            routeEditTimePickerTimeFrom.apply {
                setIs24HourView(true)
                hour = 0
                minute = 0
            }
        }
    }

    private fun setDefaultConstraints() {
        hidePickers()
        setDefaultVisibility()
        defaultConstraints.applyTo(binding.routeEditConstraints)
    }

    private fun setDefaultVisibility() {
        with(binding) {
            routeEditFragmentSaveButton.visibility = View.VISIBLE
            routeEditFragmentCancelButton.visibility = View.VISIBLE
            routeEditFragmentDateTimeLayout.visibility = View.VISIBLE
            routeEditFragmentTrainNumberLayout.visibility = View.VISIBLE
            routeEditFragmentDriverLayout.visibility = View.VISIBLE
            routeEditFragmentPeriodicityLayout.visibility = View.VISIBLE
            routeEditFragmentTimeToLayout.visibility = View.VISIBLE
            routeEditFragmentTimeRestLayout.visibility = View.VISIBLE
            routeEditFragmentTimeFromLayout.visibility = View.VISIBLE
            routeEditFragmentTrainNumberLayout.endIconDrawable =
                getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
            routeEditFragmentDriverLayout.endIconDrawable =
                getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
            routeEditFragmentPeriodicityLayout.endIconDrawable =
                getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
        }
        disembarkDateTimeTag = 0
        trainsListTag = 0
        driversListTag = 0
        periodicityListTag = 0
        timeToTag = 0
        restTag = 0
        timeFromTag = 0
    }

    private fun hidePickers() {
        with(binding) {
            routeEditFragmentDisembarkDateTimePickerLayout.visibility = View.GONE
            routeEditTimePickerTimeTo.visibility = View.GONE
            routeEditTimePickerTimeRest.visibility = View.GONE
            routeEditTimePickerTimeFrom.visibility = View.GONE
            routeEditFragmentDisembarkSetButton.visibility = View.GONE
            routeEditFragmentTimeToSetButton.visibility = View.GONE
            routeEditFragmentRestSetButton.visibility = View.GONE
            routeEditFragmentTimeFromSetButton.visibility = View.GONE
        }
    }

    private fun hideSaveCancelButtons() {
        with(binding) {
            routeEditFragmentSaveButton.visibility = View.GONE
            routeEditFragmentCancelButton.visibility = View.GONE
        }
    }

    private fun initSetButtons() {
        with(binding) {
            routeEditFragmentDisembarkSetButton.setOnClickListener {
                //todo action
            }
            routeEditFragmentTimeToSetButton.setOnClickListener {
                //todo action
            }
            routeEditFragmentRestSetButton.setOnClickListener {
                //todo action
            }
            routeEditFragmentTimeFromSetButton.setOnClickListener {
                //todo action
            }
        }
    }

    companion object {
        const val TRAIN_RUN_ID = "train_run_id"
    }
}
