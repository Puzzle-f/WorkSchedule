package com.example.workschedule.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.route_edit.RouteEditFragment.Companion.TRAIN_RUN_ID
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()
    private val adapter by lazy { MainFragmentAdapter(requireActivity().menuInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.mainFragmentRecyclerView)
    }

    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.mainFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() {
        binding.mainFragmentAddRouteFAB.setOnClickListener { findNavController().navigate(R.id.nav_route_edit) }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            mainFragmentViewModel.trainsRunList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        mainFragmentViewModel.getTrainsRunList()
        if (adapter.currentList.isNotEmpty()) {
            Toast.makeText(
                activity, getString(R.string.mainTableFilled), Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_from_context -> {
                val bundle = bundleOf(TRAIN_RUN_ID to adapter.clickedTrainRunId)
                findNavController().navigate(R.id.nav_route_edit, bundle)
            }
            R.id.action_delete_from_context -> {
                adapter.removeItem()
                mainFragmentViewModel.deleteTrainRun(adapter.clickedTrainRunId)
            }
        }
        return super.onContextItemSelected(item)
    }
}
