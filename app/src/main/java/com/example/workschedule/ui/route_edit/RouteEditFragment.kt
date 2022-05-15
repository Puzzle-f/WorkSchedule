package com.example.workschedule.ui.route_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentRouteEditBinding
import com.example.workschedule.ui.drivers.DriversFragmentAdapter
import com.example.workschedule.ui.trains.TrainsFragmentAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class RouteEditFragment : Fragment() {
    private val routeEditViewModel: RouteEditViewModel by viewModel()
    private var _binding: FragmentRouteEditBinding? = null
    private val binding: FragmentRouteEditBinding
        get() = _binding ?: throw RuntimeException("FragmentRouteEditBinding? is null")
    private val driversAdapter: DriversFragmentAdapter by lazy {
        DriversFragmentAdapter(requireActivity().menuInflater)
    }
    private val trainsAdapter: TrainsFragmentAdapter by lazy {
        TrainsFragmentAdapter(requireActivity().menuInflater)
    }
    private var trainRunId: Int? = null
    private var trainsListTag: Int = 0
    private var driversListTag: Int = 0
    private var periodicityListTag: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteEditBinding.inflate(inflater, container, false)
        arguments?.let {
            trainRunId = it.getInt(TRAIN_RUN_ID)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        trainRunId?.let {
            //todo  Если есть переданное значение во фрагмент,
            //todo  то в этом месте должен быть запрос в бд и вывод данных в интерфейс
            //todo  т.е. запуск фрагмента в режиме редактирования данных
        }
        binding.routeEditFragmentDriversRecycler.adapter = driversAdapter
        binding.routeEditFragmentTrainsRecycler.adapter = trainsAdapter
        lifecycleScope.launchWhenStarted {
            routeEditViewModel
                .trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    trainsAdapter.submitList(it)
                }
        }
        lifecycleScope.launchWhenStarted {
            routeEditViewModel
                .drivers
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    driversAdapter.submitList(it)
                }
        }
        routeEditViewModel.getDrivers()
        routeEditViewModel.getTrains()
        binding.routeEditFragmentTrainNumber.setOnClickListener {
            when (trainsListTag) {
                0 -> {
                    trainsListTag = 1
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentDriverLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                    }
                    binding.routeEditFragmentTimeFromLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeToLayout.visibility = View.GONE
                    binding.routeEditFragmentPeriodicityLayout.visibility = View.GONE
                    binding.routeEditFragmentDriverLayout.visibility = View.GONE
                }
                1 -> {
                    trainsListTag = 0
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentDriverLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToBottom = binding.routeEditFragmentTrainNumberLayout.id
                    }
                    binding.routeEditFragmentDriverLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentPeriodicityLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeToLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeFromLayout.visibility = View.VISIBLE
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
                    binding.routeEditFragmentTimeFromLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeToLayout.visibility = View.GONE
                    binding.routeEditFragmentPeriodicityLayout.visibility = View.GONE
                }
                1 -> {
                    driversListTag = 0
                    binding.routeEditFragmentDriverLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentPeriodicityLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentDriverLayout.id
                        }
                    binding.routeEditFragmentPeriodicityLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeToLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeFromLayout.visibility = View.VISIBLE
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
                    binding.routeEditFragmentTimeFromLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.GONE
                    binding.routeEditFragmentTimeToLayout.visibility = View.GONE
                }
                1 -> {
                    periodicityListTag = 0
                    binding.routeEditFragmentPeriodicityLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentTimeToLayout
                        .updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToBottom = binding.routeEditFragmentPeriodicityLayout.id
                        }
                    binding.routeEditFragmentTimeToLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeRestLayout.visibility = View.VISIBLE
                    binding.routeEditFragmentTimeFromLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRAIN_RUN_ID = "train_run_id"
    }
}
