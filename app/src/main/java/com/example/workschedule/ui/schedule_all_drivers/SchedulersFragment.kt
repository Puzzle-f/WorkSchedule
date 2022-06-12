package com.example.workschedule.ui.schedule_all_drivers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.databinding.FragmentScheduleAllDriversBinding
import com.example.workschedule.ui.base.BaseFragment
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class SchedulersFragment :
    BaseFragment<FragmentScheduleAllDriversBinding>(FragmentScheduleAllDriversBinding::inflate) {

    private val schedulersViewModel: SchedulersViewModel by viewModel()
    private val sectionAdapter = SectionedRecyclerViewAdapter()

    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.verticalRv.adapter = sectionAdapter
    }

    override fun initListeners() {}

    @SuppressLint("NotifyDataSetChanged")
    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            schedulersViewModel.data
                .collect { list ->
                    if (list.isNotEmpty()) {
                        list.forEach { model ->
                            sectionAdapter.addSection(
                                VerticalRVAdapter(
                                    model.driver,
                                    model.horizontalRVModel
                                )
                            )
                        }
                        sectionAdapter.notifyDataSetChanged()
                    }
                }
        }
        schedulersViewModel.getVerticalRVModel()
    }
}