package com.example.workschedule.ui.route_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workschedule.databinding.FragmentRouteDatetimeEditBinding

class DateTimePickerFragment : Fragment() {
    private lateinit var datetimePickerViewModel: DateTimePickerViewModel
    private var _binding: FragmentRouteDatetimeEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DateTimePickerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteDatetimeEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
