package com.example.workschedule.ui.finddriver

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentSelectionDriverBinding
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.utils.toLocalDateTime
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter

class SelectionDriverFragment :
    BaseFragment<FragmentSelectionDriverBinding>(FragmentSelectionDriverBinding::inflate) {

    private var trainRunId: Int? = null
    private val selectionDriverViewModel: SelectionDriverViewModel by viewModel()
    private val adapter by lazy { SelectionDriverAdapter(requireActivity().menuInflater, onListItemClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.selectionDriverFragmentRecyclerView)
    }

    override fun readArguments(bundle: Bundle) {
        trainRunId = bundle.getInt(TRAIN_RUN_ID_BEFORE_PLANING_HORIZON)
    }

    override fun initView() {
        binding.selectionDriverFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            trainRunId.let {
                    selectionDriverViewModel.getTrainRun(it!!)
            }
            selectionDriverViewModel.trainRun
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {trainRun ->
                    binding.itemTrainNumber.text = trainRun?.number
                    binding.startTime.text = trainRun?.startTime?.toLocalDateTime()?.format(
                        DateTimeFormatter.ofPattern("dd.MM.yy  HH:mm")
                    )
                    if(trainRun!=null){
                        selectionDriverViewModel.getDrivers(trainRun).join()
                        selectionDriverViewModel.getStatuses(trainRun)
                        selectionDriverViewModel.getSelectionDriverData(trainRun)
                    }
                }
        }
        initProgressBar()
    }

    override fun initListeners() {

    }

    private val onListItemClickListener: SelectionDriverAdapter.OnListItemClickListener =
        object : SelectionDriverAdapter.OnListItemClickListener {
            override fun onItemClick(driverId: Int) {
                lifecycleScope.launchWhenStarted {
                    selectionDriverViewModel.updateTrainRun(driverId).join()
                    selectionDriverViewModel.cleanDriverForTrainRun(driverId)
                    findNavController().navigate(R.id.nav_main)
                }
            }
        }

    override fun initObservers() {
        trainRunId?.let { trainRunId ->
            lifecycleScope.launchWhenStarted {
                selectionDriverViewModel
                    .dataVisual
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { visualData ->
                        adapter.submitList(visualData)
                        adapter.notifyDataSetChanged()
                    }
            }
        }
    }

    fun initProgressBar(){
        lifecycleScope.launchWhenStarted {
            selectionDriverViewModel.showProgress
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect{
                    if(it!=null&& it)
                        binding.progress.visibility = View.VISIBLE
                    else binding.progress.visibility = View.INVISIBLE
                }
        }
    }

    companion object {
        const val TRAIN_RUN_ID_BEFORE_PLANING_HORIZON = "TRAIN_RUN_ID_BEFORE_PLANING_HORIZON"
    }
}