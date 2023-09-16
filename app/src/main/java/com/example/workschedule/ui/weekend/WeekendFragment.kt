package com.example.workschedule.ui.weekend

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentWeekendBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment
import com.example.workschedule.utils.toLocalDateTime
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class WeekendFragment :
    BaseFragment<FragmentWeekendBinding>(FragmentWeekendBinding::inflate) {

    private val weekendViewModel: WeekendViewModel by viewModel()
    private val adapter: WeekendFragmentAdapter by lazy { WeekendFragmentAdapter(requireActivity().menuInflater) }
    private var driverId: Int? = null
    private var selectedDate = LocalDate.now()

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DriverEditFragment.DRIVER_ID)
    }

    override fun initView() {
        binding.weekendsRecyclerView.adapter = adapter
    }

    override fun initListeners() {
        with(binding) {
            addWeekendButton.setOnClickListener {
                calendar.setOnDateChangeListener { calendarView, y, m, day ->
                    selectedDate = LocalDate.of(y, m+1, day)
                }
                weekendViewModel.saveWeekend(driverId!!, selectedDate)
                weekendViewModel.getWeekends(driverId!!)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            weekendViewModel.weekends
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect{ listWeekend ->
                    adapter.submitList(listWeekend  .filter { it.startWeekend })
                }
        }
        weekendViewModel.getWeekends(driverId!!)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_delete_weekend_from_context -> {
                adapter.removeItem()
                weekendViewModel.deleteWeekend(adapter.clickedWeekendDriverId,
                    adapter.clickedWeekendDate.toLocalDateTime().toLocalDate())
            }
            R.id.action_delete_all_weekend_from_context -> {
                adapter.removeAll()
                weekendViewModel.deleteAllWeekendsForDriver(adapter.clickedWeekendDriverId)
            }
        }
        return super.onContextItemSelected(item)
    }

}