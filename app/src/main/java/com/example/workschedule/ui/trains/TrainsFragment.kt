package com.example.workschedule.ui.trains

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.databinding.FragmentTrainsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TrainsFragment : Fragment() {

    private val trainsViewModel: TrainsViewModel by viewModel()
    private var _binding: FragmentTrainsBinding? = null
    private val binding get() = _binding?: throw RuntimeException("FragmentTrainsBinding? = null")
    private val adapter: TrainsFragmentAdapter by lazy { TrainsFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trainsFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            trainsViewModel.trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
            trainsViewModel.getTrains()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
