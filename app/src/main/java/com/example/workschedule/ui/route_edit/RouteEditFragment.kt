package com.example.workschedule.ui.route_edit;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentRouteEditBinding

class RouteEditFragment: Fragment() {
    private lateinit var routeEditViewModel: RouteEditViewModel
    private var _binding: FragmentRouteEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RouteEditViewModel by viewModels()
    private var trainsListTag: Int = 0
    private var driversListTag: Int = 0
    private var periodicityListTag: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        binding.routeEditFragmentTrainNumber.setOnClickListener {
            when (trainsListTag) {
                0 -> {
                    trainsListTag = 1
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentDriverLayout.
                        updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                        }
                }
                1 -> {
                    trainsListTag = 0
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentDriverLayout.
                        updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentTrainNumberLayout.id
                        }
                }
            }
        }

        binding.routeEditFragmentDriver.setOnClickListener {
            when (driversListTag) {
                0 -> {
                    driversListTag = 1
                    binding.routeEditFragmentDriverLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentPeriodicityLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                        }
                }
                1 -> {
                    driversListTag = 0
                    binding.routeEditFragmentDriverLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentPeriodicityLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentDriverLayout.id
                        }
                }
            }
        }

        binding.routeEditFragmentPeriodicity.setOnClickListener {
            when (periodicityListTag) {
                0 -> {
                    periodicityListTag = 1
                    binding.routeEditFragmentPeriodicityLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentTimeToLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                        }
                }
                1 -> {
                    periodicityListTag = 0
                    binding.routeEditFragmentPeriodicityLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentTimeToLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentPeriodicityLayout.id
                        }
                }
            }
        }
    }
}
