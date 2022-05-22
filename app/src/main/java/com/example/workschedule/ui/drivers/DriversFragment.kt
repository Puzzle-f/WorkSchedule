package com.example.workschedule.ui.drivers

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriversBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment.Companion.DRIVER_ID
import org.koin.android.viewmodel.ext.android.viewModel

class DriversFragment : BaseFragment<FragmentDriversBinding>(FragmentDriversBinding::inflate) {

    private val driversViewModel: DriversViewModel by viewModel()
    private val adapter: DriversFragmentAdapter by lazy { DriversFragmentAdapter(requireActivity().menuInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.driversFragmentRecyclerView)
    }

    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.driversFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() {
        binding.driversFragmentAddDriverFAB.setOnClickListener {
            it.findNavController().navigate(R.id.nav_driver_edit)
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            driversViewModel.drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        driversViewModel.getDrivers()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_driver_from_context -> {
                val bundle = bundleOf(DRIVER_ID to adapter.clickedDriverId)
                findNavController().navigate(R.id.nav_driver_edit, bundle)
            }
            R.id.action_delete_driver_from_context -> {
                adapter.removeItem()
                driversViewModel.deleteDriver(adapter.clickedDriverId)
            }
        }
        return super.onContextItemSelected(item)
    }
}