package com.example.workschedule.ui.route_edit;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.workschedule.databinding.FragmentRouteEditBinding

class RouteEditFragment: Fragment() {
    private lateinit var routeEditViewModel: RouteEditViewModel
    private var _binding: FragmentRouteEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        routeEditViewModel =
            ViewModelProvider(this)[RouteEditViewModel::class.java]

        _binding = FragmentRouteEditBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
