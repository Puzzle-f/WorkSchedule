package com.example.workschedule.ui.driver_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.ui.main.DriverEditViewModel

class DriverEditFragment : Fragment() {

    private lateinit var driverEditViewModel: DriverEditViewModel
    private var _binding: FragmentDriverEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        driverEditViewModel =
            ViewModelProvider(this)[DriverEditViewModel::class.java]

        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        driverEditViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
