package com.example.workschedule.ui.drivers

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriversBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment.Companion.DRIVER_ID
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class DriversFragment : BaseFragment<FragmentDriversBinding>(FragmentDriversBinding::inflate) {
    private val driversViewModel: DriversViewModel by viewModel()
    private val adapter: DriversFragmentAdapter by lazy { DriversFragmentAdapter(requireActivity().menuInflater) }
    private lateinit var buttonNewDriver: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonNewDriver = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_driver)
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.driversFragmentRecyclerView)
    }

    override fun onStart() {
        buttonNewDriver.visibility = View.VISIBLE
        super.onStart()
    }
    
    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.driversFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() {

        buttonNewDriver.setOnClickListener {
            findNavController().navigate(R.id.action_nav_drivers_to_nav_driver_edit)
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
                findNavController().navigate(R.id.action_nav_drivers_to_nav_driver_edit, bundle)
            }
            R.id.action_delete_driver_from_context -> {
                adapter.removeItem()
                driversViewModel.deleteDriver(adapter.clickedDriverId)
            }
            R.id.action_delete_all_drivers_from_context ->{
                adapter.removeAll()
                driversViewModel.deleteAllDrivers()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onStop() {
        buttonNewDriver.visibility = View.GONE
        super.onStop()
    }
}
