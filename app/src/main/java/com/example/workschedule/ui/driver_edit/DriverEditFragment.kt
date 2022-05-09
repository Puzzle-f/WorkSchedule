package com.example.workschedule.ui.driver_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.ui.worker_edit.WorkerEditAdapter

class DriverEditFragment : Fragment() {
    private var _binding: FragmentDriverEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DriverEditViewModel by viewModels()
    private val adapter: WorkerEditAdapter by lazy { WorkerEditAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editDriverFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel.directions
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        viewModel.getDirections()
        initButtons()
    }


    fun initButtons() {
        binding.driverEditFragmentCancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
        binding.driverEditFragmentSaveButton.setOnClickListener {
            if (binding.driverEditFragmentId.text.toString().isNotBlank() &&
                binding.driverEditFragmentSurname.text.toString().isNotBlank() &&
                binding.driverEditFragmentName.text.toString().isNotBlank() &&
                binding.driverEditFragmentPatronymic.text.toString().isNotBlank()
            ) {
                lifecycleScope.launchWhenStarted {
                    viewModel.createDriver(
                        binding.driverEditFragmentId.text.toString().toInt(),
                        binding.driverEditFragmentSurname.text.toString(),
                        binding.driverEditFragmentName.text.toString(),
                        binding.driverEditFragmentPatronymic.text.toString(),
                        0,
                        0,
                        listOf(1)
                    )
                    Toast.makeText(
                        getActivity(),
                        "Данные работника с id=${binding.driverEditFragmentId.text.toString()} успешно добавлены",
                        Toast.LENGTH_LONG
                    ).show()
                    it.findNavController().navigateUp()
                }
            } else Toast.makeText(
                getActivity(), "Введите корректные данные",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}