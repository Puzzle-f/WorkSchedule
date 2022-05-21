package com.example.workschedule.ui.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriversBinding
import com.example.workschedule.ui.driver_edit.DriverEditFragment.Companion.DRIVER_ID
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class DriversFragment : Fragment() {
    private val driversViewModel: DriversViewModel by viewModel()
    private var _binding: FragmentDriversBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentDriversBinding? = null")
    private val adapter: DriversFragmentAdapter by lazy { DriversFragmentAdapter(requireActivity().menuInflater) }
    private lateinit var buttonNewDriver: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriversBinding.inflate(inflater, container, false)
        buttonNewDriver = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_driver)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.driversFragmentRecyclerView)
        initView()
    }

    override fun onStart() {
        buttonNewDriver.visibility = View.VISIBLE
        super.onStart()
    }

    private fun initView() {
        binding.driversFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            driversViewModel.drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        driversViewModel.getDrivers()

        buttonNewDriver.setOnClickListener {
            findNavController().navigate(R.id.nav_driver_edit)
        }
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

    override fun onStop() {
        buttonNewDriver.visibility = View.GONE
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
