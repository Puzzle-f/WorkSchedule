package com.example.workschedule.ui.driver_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workschedule.databinding.FragmentDriverEditBinding

class DriverEditFragment : Fragment() {
    private lateinit var driverEditViewModel: DriverEditViewModel
    private var _binding: FragmentDriverEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DriverEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
