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
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.finddriver.SelectionDriverFragment.Companion.TRAIN_RUN_ID_BEFORE_PLANING_HORIZON
import com.example.workschedule.ui.settings.PLANNING_HORIZON
import com.example.workschedule.ui.trainrun_edit.TrainRunEditFragment.Companion.TRAIN_RUN_ID
import com.example.workschedule.utils.toLong
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()
    private val adapter by lazy {
        MainFragmentAdapter(
            requireActivity().menuInflater,
            mainFragmentViewModel.borderHorizon
        )
    }
    private lateinit var buttonNewRoute: MaterialButton
    private lateinit var buttonRecalculate: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonNewRoute = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_route)
        buttonRecalculate = (activity as AppCompatActivity).findViewById(R.id.recalculation)
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.mainFragmentRecyclerView)
    }

    override fun onStart() {
        buttonNewRoute.visibility = View.VISIBLE
        buttonRecalculate.visibility = View.VISIBLE
        super.onStart()
    }

    override fun readArguments(bundle: Bundle) {
    }

    override fun initView() {
        binding.mainFragmentRecyclerView.adapter = adapter
        selectStartPosition()
        initProgressBar()
    }

    override fun initListeners() {
        buttonNewRoute.setOnClickListener {
            findNavController().navigate(R.id.action_nav_main_to_nav_route_edit)
        }
        buttonRecalculate.setOnClickListener {
            val dateNow = LocalDateTime.now()
            mainFragmentViewModel.checkWeekendAllTrainRun()
            if (mainFragmentViewModel.trainRunList.value.any {
                    it.startTime <= dateNow.toLong() + PLANNING_HORIZON &&
                            it.driverId == 0
                }) {
                finDriverBeforeHorizonPlaning()
                return@setOnClickListener
            }
            mainFragmentViewModel.findDriverAfterHorizon()
            Toast.makeText(activity, "Наряд заполнен", Toast.LENGTH_LONG).show()
        }
        binding.mainFragmentRecyclerView.layoutManager!!.scrollToPosition(20)

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            mainFragmentViewModel.data
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { data ->
                    if (data.isNotEmpty()) {
                        adapter.submitList(data)
                        adapter.notifyDataSetChanged()
                    }
                }
        }
        mainFragmentViewModel.getMainFragmentData()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_from_context -> {
                val bundle = bundleOf(TRAIN_RUN_ID to adapter.clickedTrainRunId)
                findNavController().navigate(R.id.action_nav_main_to_nav_route_edit, bundle)
            }
            R.id.action_delete_from_context -> {
                mainFragmentViewModel.deleteTrainRun(adapter.clickedTrainRunId)
                adapter.removeItem()
            }
            R.id.action_delete_all_from_context -> {
                mainFragmentViewModel.deleteAllTrainRun()
                adapter.removeAllItems()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun finDriverBeforeHorizonPlaning() {
        val trainRunId = mainFragmentViewModel.trainRunList.value
            .filter { it.driverId == 0 }
            .sortedBy { it.startTime }
            .map { it.id }.first()
        val bundle = bundleOf(TRAIN_RUN_ID_BEFORE_PLANING_HORIZON to trainRunId)
        findNavController().navigate(
            R.id.action_nav_main_to_nav_selection_driver, bundle
        )
    }

    private fun selectStartPosition() {
        var isSelectToPosition = true
        val currentData = LocalDateTime.now()
        var position: Int
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (adapter.currentList.isNotEmpty()
                    && isSelectToPosition
                ) {
                    position = adapter.currentList.indexOf(adapter.currentList.first { it.data >= currentData.toLong() })
                    binding.mainFragmentRecyclerView.scrollToPosition(position)
                    isSelectToPosition = !isSelectToPosition
                }
            }
        })
    }

    fun initProgressBar(){
        lifecycleScope.launchWhenStarted {
            mainFragmentViewModel.showProgress
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect{
                if(it!=null&& it)
                    binding.progressMain.visibility = View.VISIBLE
                    else binding.progressMain.visibility = View.INVISIBLE
            }
        }
    }

    override fun onStop() {
        buttonNewRoute.visibility = View.GONE
        buttonRecalculate.visibility = View.GONE
        adapter.removeAllItems()
        super.onStop()
    }
}
