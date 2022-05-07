package com.example.workschedule.ui.train_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.domain.models.Train
import com.example.workschedule.databinding.FragmentTrainEditBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TrainEditFragment : Fragment() {

    private val trainEditViewModel: TrainEditViewModel by viewModel()
    private var _binding: FragmentTrainEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditTrainBinding? = null")
    private var trainNumber: Int? = null

    // Вызов фрагмента:
    // val bundle = bundleOf(TRAIN_NUMBER to Number)
    // view.findNavController().navigate(R.id.nav_train_edit, bundle)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainEditBinding.inflate(inflater, container, false)
        arguments?.let {
            trainNumber = it.getInt(TRAIN_NUMBER)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainNumber?.let {
            lifecycleScope.launchWhenStarted {
                trainEditViewModel.train
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { train ->
                        train?.let { trainNotNull ->
                            binding.trainEditFragmentTrainNumber.setText(trainNotNull.number)
                            binding.trainEditFragmentDirection.setText(trainNotNull.direction)
                        }
                    }
            }
            trainEditViewModel.getTrain(it)
        }
        binding.trainEditFragmentSaveButton.setOnClickListener {
            trainEditViewModel.saveTrain(
                Train(
                    binding.trainEditFragmentTrainNumber.text.toString().toInt(),
                    binding.trainEditFragmentDirection.text.toString()
                )
            )
        }
        binding.trainEditFragmentCancelButton.setOnClickListener {
            findNavController().backQueue
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
        private const val TRAIN_NUMBER = "train_number"
    }
}