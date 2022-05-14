package com.example.workschedule.ui.train_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.databinding.FragmentTrainEditBinding
import com.example.workschedule.domain.models.Train
import org.koin.android.viewmodel.ext.android.viewModel

class TrainEditFragment : Fragment() {

    private val trainEditViewModel: TrainEditViewModel by viewModel()
    private var _binding: FragmentTrainEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentTrainEditBinding? = null")
    private var trainNumber: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainEditBinding.inflate(inflater, container, false)
        arguments?.let {
            trainNumber = it.getInt(TRAIN_NUMBER)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        trainNumber?.let {
            lifecycleScope.launchWhenStarted {
                trainEditViewModel.train
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { train ->
                        train?.let { trainNotNull ->
                            binding.trainEditFragmentTrainNumber.setText("${trainNotNull.number}")
                            binding.trainEditFragmentDirection.setText(trainNotNull.direction)
                        }
                    }
            }
            trainEditViewModel.getTrain(it)
        }
        binding.trainEditFragmentSaveButton.setOnClickListener {
            trainEditViewModel.saveTrain(
                Train(
                    0,
                    binding.trainEditFragmentTrainNumber.text.toString().toInt(),
                    binding.trainEditFragmentDirection.text.toString()
                )
            )
            Toast.makeText(
                activity,
                "Поезд с №${binding.trainEditFragmentTrainNumber.text.toString()} успешно добавлен",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }
        binding.trainEditFragmentCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.trainEditFragmentTrainNumber.doAfterTextChanged {
            when {
                it?.isNotEmpty() == true && binding.trainEditFragmentDirection.text?.isNotEmpty() == true ->
                    binding.trainEditFragmentSaveButton.isEnabled = true
                else -> binding.trainEditFragmentSaveButton.isEnabled = false
            }
        }
        binding.trainEditFragmentDirection.doAfterTextChanged {
            when {
                it?.isNotEmpty() == true && binding.trainEditFragmentTrainNumber.text?.isNotEmpty() == true ->
                    binding.trainEditFragmentSaveButton.isEnabled = true
                else -> binding.trainEditFragmentSaveButton.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRAIN_NUMBER = "train_number"
    }
}