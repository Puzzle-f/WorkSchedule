package com.example.workschedule.ui.worker_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.workschedule.databinding.FragmentDriverEditBinding
import com.example.workschedule.ui.main.WorkerEditViewModel

class WorkerEditFragment : Fragment() {

    private lateinit var workerEditViewModel: WorkerEditViewModel
    private var _binding: FragmentDriverEditBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workerEditViewModel =
            ViewModelProvider(this)[WorkerEditViewModel::class.java]

        _binding = FragmentDriverEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        workerEditViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}