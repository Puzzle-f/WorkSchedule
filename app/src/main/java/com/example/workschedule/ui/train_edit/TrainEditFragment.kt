package com.example.workschedule.ui.train_edit

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainEditBinding
import com.example.workschedule.domain.models.Train
import com.example.workschedule.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class TrainEditFragment : BaseFragment<FragmentTrainEditBinding>(FragmentTrainEditBinding::inflate) {

    private val trainEditViewModel: TrainEditViewModel by viewModel()
    private var trainId: Int? = null

    override fun readArguments(bundle: Bundle) {
        trainId = bundle.getInt(TRAIN_ID)
    }

    override fun initView() {}

    override fun initListeners() {
        binding.trainEditFragmentCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.trainEditFragmentSaveButton.setOnClickListener {
            trainEditViewModel.saveTrain(
                Train(0, binding.trainEditFragmentDirection.text.toString())
            )
            Toast.makeText(activity, getString(R.string.trainEditTrainAdded), Toast.LENGTH_LONG)
                .show()
            findNavController().navigateUp()
        }
        binding.trainEditFragmentDirection.doAfterTextChanged {
            when {
                it?.isNotEmpty() == true ->
                    binding.trainEditFragmentSaveButton.isEnabled = true
                else -> binding.trainEditFragmentSaveButton.isEnabled = false
            }
        }
    }

    override fun initObservers() {
        trainId?.let {
            lifecycleScope.launchWhenStarted {
                trainEditViewModel.train
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { train ->
                        train?.let { trainNotNull ->
                            binding.trainEditFragmentDirection.setText(trainNotNull.direction)
                        }
                    }
            }
            trainEditViewModel.getTrain(it)
        }
    }

    companion object {
        const val TRAIN_ID = "train_id"
    }
}
