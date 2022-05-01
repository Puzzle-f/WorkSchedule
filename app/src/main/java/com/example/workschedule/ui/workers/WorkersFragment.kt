package com.example.workschedule.ui.workers

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

class WorkersFragment : Fragment() {

    private val workersViewModel: WorkersViewModel by viewModel()
    private var _binding: FragmentDriversBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentTrainsBinding? = null")
    private val adapter: WorkersAdapter by lazy { WorkersAdapter() }

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
            workersViewModel.drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        workersViewModel.getDrivers()
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