package com.example.workschedule.ui.weekend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentWeekendBinding
import com.example.workschedule.domain.models.Distraction
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment
import com.example.workschedule.utils.FIO
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toLong
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class WeekendFragment :
    BaseFragment<FragmentWeekendBinding>(FragmentWeekendBinding::inflate) {

    private val weekendViewModel: WeekendViewModel by viewModel()
    private val adapter: WeekendFragmentAdapter by lazy { WeekendFragmentAdapter(requireActivity().menuInflater) }
    private val distractionAdapter: DistractionFragmentAdapter by lazy {
        DistractionFragmentAdapter(
            requireActivity().menuInflater
        )
    }
    private var driverId: Int? = null

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DriverEditFragment.DRIVER_ID)
    }

    override fun initView() {
        binding.weekendsRecyclerView.adapter = adapter
        binding.distractionRecyclerView.adapter = distractionAdapter
//        weekendViewModel.driver.collect{
//
//        }
    }

    override fun initListeners() {
        var selectedDate = LocalDate.now()
        var currentDistractionStatus: Distraction? = null
        with(binding) {
            calendar.setOnDateChangeListener { calendarView, y, m, day ->
                selectedDate = LocalDate.of(y, m + 1, day)
                lifecycleScope.launchWhenStarted {
                    currentDistractionStatus = weekendViewModel.getLastDistractionStatus(driverId!!, selectedDate.atStartOfDay().toLong())
                if(definitionDistractionStatus(currentDistractionStatus)) addSickLeaveButton.text = resources.getText(R.string.ready_to_work)
                    else addSickLeaveButton.text = resources.getText(R.string.sick_leave)
                }
            }
            addWeekendButton.setOnClickListener {
                weekendViewModel.saveWeekend(driverId!!, selectedDate)
            }
            addSickLeaveButton.setOnClickListener {
                weekendViewModel.saveDistraction(
                    driverId!!,
                    selectedDate,
                    isDistractionStart = !definitionDistractionStatus(currentDistractionStatus)
                )
            }
        }
    }

    private fun definitionDistractionStatus(currentDistractionStatus: Distraction?): Boolean =
        currentDistractionStatus?.isDistracted?:false


    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            weekendViewModel.driver
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect{
                    if (it!=null)binding.nameDriverTv.text = it.FIO
            }
        }
        lifecycleScope.launchWhenStarted {
            weekendViewModel.weekends
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { listWeekend ->
                    adapter.submitList(listWeekend.filter { it.startWeekend })
                }
        }
        lifecycleScope.launchWhenStarted {
            weekendViewModel.distractions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { listDistraction ->
                    distractionAdapter.submitList(listDistraction)
                }
        }
        weekendViewModel.getWeekends(driverId!!)
        weekendViewModel.getDistractions(driverId!!)
        weekendViewModel.getDriver(driverId!!)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_weekend_from_context -> {
                if(distractionAdapter.clickedDistractionDriverId >= 0){
//                    distractionAdapter.removeItem()
                    weekendViewModel.deleteDistraction(distractionAdapter.clickedDistractionDriverId,
                    distractionAdapter.clickedDistractionDate.toLocalDateTime().toLocalDate())
                } else {
//                    adapter.removeItem()
                    weekendViewModel.deleteWeekend(
                        adapter.clickedWeekendDriverId,
                        adapter.clickedWeekendDate.toLocalDateTime().toLocalDate()
                    )
                }
            }
            R.id.action_delete_all_weekend_from_context -> {
                if(distractionAdapter.clickedDistractionDriverId >= 0){
                    distractionAdapter.removeAll()
                    weekendViewModel.deleteAllDistractionForDriver(distractionAdapter.clickedDistractionDriverId)
                } else {
                    adapter.removeAll()
                    weekendViewModel.deleteAllWeekendsForDriver(adapter.clickedWeekendDriverId)
                }
            }
        }
        return super.onContextItemSelected(item)
    }


}