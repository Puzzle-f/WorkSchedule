package com.example.workschedule.ui.worker_edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.workschedule.data.database.DriverDataBase
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.ui.main.WorkerEditViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class WorkerEditFragment : Fragment() {

    private val workerEditViewModel: WorkerEditViewModel by viewModel()
    private var _binding: FragmentDriverEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDriverEditBinding? = null")
    private val adapter: WorkerEditAdapter by lazy { WorkerEditAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editDriverFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            workerEditViewModel.directions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        workerEditViewModel.getDirections()
        initButtons()
    }


    fun initButtons() {
        binding.driverEditFragmentCancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
        binding.driverEditFragmentSaveButton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                workerEditViewModel.saveNewDriverFake()
            }
            it.findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}