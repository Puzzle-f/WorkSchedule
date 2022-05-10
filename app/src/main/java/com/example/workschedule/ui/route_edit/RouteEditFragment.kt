package com.example.workschedule.ui.route_edit;

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
import com.example.workschedule.ui.train_edit.TrainEditFragment
import org.koin.android.viewmodel.ext.android.viewModel

class RouteEditFragment : Fragment() {
    private var _binding: FragmentRouteEditBinding? = null
    private val binding get() = _binding!!
    private val routeEditViewModel: RouteEditViewModel by viewModel()
    private var trainRunId: Int? = null
    private var trainsListTag: Int = 0
    private var driversListTag: Int = 0
    private var periodicityListTag: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteEditBinding.inflate(inflater, container, false)
        arguments?.let {  // Чтение из бандла переданных данных, если они есть
            trainRunId = it.getInt(TRAIN_RUN_ID)
        }
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
        trainRunId?.let {
            //todo  Если есть переданное значение во фрагмент,
            //todo  то в этом месте должен быть запрос в бд и вывод данных в интерфейс
            //todo  т.е. запуск фрагмента в режиме редактирования данных
        }
        binding.routeEditFragmentTrainNumber.setOnClickListener {
            when (trainsListTag) {
                0 -> {
                    trainsListTag = 1
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_squash_up)
                    binding.routeEditFragmentDriverLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToBottom = binding.routeEditFragmentGuidelineRecycler.id
                    }
                }
                1 -> {
                    trainsListTag = 0
                    binding.routeEditFragmentTrainNumberLayout.endIconDrawable =
                        getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
                    binding.routeEditFragmentDriverLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
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

    companion object {
        const val TRAIN_RUN_ID = "train_run_id"
    }
}
