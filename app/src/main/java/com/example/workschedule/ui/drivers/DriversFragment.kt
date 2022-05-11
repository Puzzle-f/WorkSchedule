package com.example.workschedule.ui.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.databinding.FragmentDriversBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DriversFragment : Fragment() {

    private val driversViewModel: DriversViewModel by viewModel()
    private var _binding: FragmentDriversBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentTrainsBinding? = null")
    private val adapter: DriversFragmentAdapter by lazy { DriversFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.driversFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            driversViewModel.drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        driversViewModel.getDrivers()
    }

    private fun setClickListenerFloatingButton(){
        binding.driversFragmentAddDriverFAB.setOnClickListener {


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}