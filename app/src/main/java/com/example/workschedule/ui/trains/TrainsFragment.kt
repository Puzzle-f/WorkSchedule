package com.example.workschedule.ui.trains

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainsBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.train_edit.TrainEditFragment.Companion.TRAIN_ID
import org.koin.android.viewmodel.ext.android.viewModel

class TrainsFragment : BaseFragment<FragmentTrainsBinding>(FragmentTrainsBinding::inflate) {

    private val trainsViewModel: TrainsViewModel by viewModel()
    private val adapter: TrainsFragmentAdapter by lazy { TrainsFragmentAdapter(requireActivity().menuInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.trainsFragmentRecyclerView)
    }

    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.trainsFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() {
        binding.trainsFragmentAddTrainFAB.setOnClickListener { findNavController().navigate(R.id.nav_train_edit) }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            trainsViewModel.trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        trainsViewModel.getTrains()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_train_from_context -> {
                val bundle = bundleOf(TRAIN_ID to adapter.clickedId)
                findNavController().navigate(R.id.nav_train_edit, bundle)
            }
            R.id.action_delete_train_from_context -> {
                adapter.removeItem()
                trainsViewModel.deleteTrain(adapter.clickedId)
            }
        }
        return super.onContextItemSelected(item)
    }
}
