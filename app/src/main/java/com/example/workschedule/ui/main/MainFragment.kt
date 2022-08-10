package com.example.workschedule.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.trainrun_edit.TrainRunEditFragment.Companion.TRAIN_RUN_ID
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()
    private val adapter by lazy { MainFragmentAdapter(requireActivity().menuInflater) }
    private lateinit var buttonNewRoute: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonNewRoute = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_route)
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.mainFragmentRecyclerView)
//        binding.mainFragmentRecyclerView.getLayoutManager()?.scrollToPosition(15)
    }

    override fun onStart() {
        buttonNewRoute.visibility = View.VISIBLE
        super.onStart()
    }
    
    override fun readArguments(bundle: Bundle) {}

    override fun initView() {
        binding.mainFragmentRecyclerView.adapter = adapter
    }

    override fun initListeners() {
        buttonNewRoute.setOnClickListener {
            findNavController().navigate(R.id.action_nav_main_to_nav_route_edit)
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            mainFragmentViewModel.trainsRunList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                    if (it.isNotEmpty()) {
                        Toast.makeText(
                            activity, getString(R.string.mainTableFilled), Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        mainFragmentViewModel.getTrainsRunList()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_from_context -> {
                val bundle = bundleOf(TRAIN_RUN_ID to adapter.clickedTrainRunId)
                findNavController().navigate(R.id.action_nav_main_to_nav_route_edit, bundle)
            }
            R.id.action_delete_from_context -> {
                adapter.removeItem()
                mainFragmentViewModel.deleteTrainRun(adapter.clickedTrainRunId)
            }
            R.id.action_delete_all_from_context -> {
                mainFragmentViewModel.deleteAllTrainRun()
                adapter.removeAllItems()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onStop() {
        buttonNewRoute.visibility = View.GONE
        super.onStop()
    }
}
