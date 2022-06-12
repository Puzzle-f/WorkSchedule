package com.example.workschedule.ui.schedule_all_drivers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.databinding.FragmentScheduleAllDriversBinding
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class SchedulersFragment : Fragment() {

    private val schedulersViewModel: SchedulersViewModel by viewModel()
    private val sectionAdapter = SectionedRecyclerViewAdapter()
    private var _binding: FragmentScheduleAllDriversBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleAllDriversBinding? = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleAllDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayoutManager()
        getData()
    }

    private fun initLayoutManager() {
        binding.verticalRv.adapter = sectionAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
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