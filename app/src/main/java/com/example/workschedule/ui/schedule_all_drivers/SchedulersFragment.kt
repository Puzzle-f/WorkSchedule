package com.example.workschedule.ui.schedule_all_drivers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workschedule.databinding.FragmentScheduleAllDriversBinding
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class SchedulersFragment : Fragment() {

    val schedulersViewModel: SchedulersViewModel by viewModel()
    private val sectionAdapter = SectionedRecyclerViewAdapter()
    private var _binding: FragmentScheduleAllDriversBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleAllDriversBinding? = null")
    private val listVerticalRVAdapter = mutableListOf<Vertical_RVAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleAllDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayoutManager()
//        getDataFromViewModel()
        getHardData()
        checkDrivers()
        checkData()
    }

    private fun initLayoutManager() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.verticalRv.layoutManager = layoutManager
        binding.verticalRv.adapter = sectionAdapter
    }

    fun checkData(){
        lifecycleScope.launchWhenStarted {
            schedulersViewModel.data.flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect{
                println("data = $it")
            }
        }
    }
    fun checkDrivers(){
        lifecycleScope.launchWhenStarted {
            schedulersViewModel.drivers.flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect{
                println("drivers = $it")
            }
        }
    }

    fun getHardData() {
        schedulersViewModel.getVerticalRVModel()
        lifecycleScope.launchWhenStarted {
            schedulersViewModel.data.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it.isNotEmpty()) {
                        for (i in it) {
                            listVerticalRVAdapter.add(
                                Vertical_RVAdapter(
                                    i.driver,
                                    i.horizontalRVModel
                                )
                            )
                        }
                    }
                }
        }
        for (i in listVerticalRVAdapter) {
            sectionAdapter.addSection(i)
            println(i)
        }
//        for (i in hardData) {
//            sectionAdapter.addSection(
//                Vertical_RVAdapter(
//                    i.driver, i.horizontalRVModel
//                )
//            )
//            println(i)
//        }
    }


}