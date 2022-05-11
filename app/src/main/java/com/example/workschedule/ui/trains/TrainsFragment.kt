package com.example.workschedule.ui.trains

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainsBinding
import com.example.workschedule.ui.train_edit.TrainEditFragment.Companion.TRAIN_NUMBER
import org.koin.android.viewmodel.ext.android.viewModel

class TrainsFragment : Fragment() {

    private val trainsViewModel: TrainsViewModel by viewModel()
    private var _binding: FragmentTrainsBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentTrainsBinding? = null")
    private val adapter: TrainsFragmentAdapter by lazy { TrainsFragmentAdapter(requireActivity().menuInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.trainsFragmentRecyclerView)
        initView()
    }

    private fun initView() {
        binding.trainsFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            trainsViewModel.trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        trainsViewModel.getTrains()
        binding.trainsFragmentAddTrainFAB.setOnClickListener { findNavController().navigate(R.id.nav_train_edit) }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_train_from_context -> {
                val bundle = bundleOf(TRAIN_NUMBER to adapter.clickedTrainNumber)
                findNavController().navigate(R.id.nav_train_edit, bundle)
            }
            R.id.action_delete_train_from_context -> {
                adapter.removeItem()
                trainsViewModel.deleteTrain(adapter.clickedTrainNumber)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
