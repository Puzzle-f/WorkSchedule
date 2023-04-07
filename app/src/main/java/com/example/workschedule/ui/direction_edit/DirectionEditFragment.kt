package com.example.workschedule.ui.direction_edit

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDirectionEditBinding
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class DirectionEditFragment :
    BaseFragment<FragmentDirectionEditBinding>(FragmentDirectionEditBinding::inflate) {

    private val trainEditViewModel: DirectionEditViewModel by viewModel()
    private var trainId: Int? = null

    internal data class EditTextValidation(
        var validTrainDirection: Boolean = false
    )

    private val editTextValidation = EditTextValidation()

    override fun readArguments(bundle: Bundle) {
        trainId = bundle.getInt(TRAIN_ID)
    }

    override fun initView() {}

    override fun initListeners() {
        binding.trainEditFragmentDirection.addTextChangedListener { text ->
            editTextValidation.validTrainDirection = !text.isNullOrBlank()
            checkSaveButtonEnable()
        }
        binding.trainEditFragmentSaveButton.setOnClickListener {
            trainEditViewModel.saveDirection(
                Direction(trainId ?: 0, binding.trainEditFragmentDirection.text.toString())
            )
            Toast.makeText(activity, getString(R.string.trainEditTrainAdded), Toast.LENGTH_LONG)
                .show()
            findNavController().navigateUp()
        }
        binding.trainEditFragmentCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkSaveButtonEnable() = with(editTextValidation) {
        binding.trainEditFragmentSaveButton.isEnabled = validTrainDirection
    }

    override fun initObservers() {
        trainId?.let {
            lifecycleScope.launchWhenStarted {
                trainEditViewModel.direction
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { direction ->
                        direction?.let { trainNotNull ->
                            binding.trainEditFragmentDirection.setText(trainNotNull.name)
                        }
                    }
            }
            trainEditViewModel.getDirection(it)
        }
    }

    companion object {
        const val TRAIN_ID = "direction_id"
    }
}
