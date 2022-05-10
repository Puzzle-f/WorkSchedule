package com.example.workschedule.ui.main

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
import com.example.workschedule.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

const val TRAIN_RUN_ID = "train_run_id"

class MainFragment : Fragment(), View.OnCreateContextMenuListener {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? = null")

    private val adapter by lazy {
        MainFragmentAdapter(requireActivity().menuInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.mainFragmentRecyclerView)
        binding.mainFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            mainFragmentViewModel.trainsRunList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        mainFragmentViewModel.getTrainsRunList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
