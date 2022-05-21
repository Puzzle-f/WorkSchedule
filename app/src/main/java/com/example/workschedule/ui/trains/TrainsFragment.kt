package com.example.workschedule.ui.trains

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainsBinding
import com.example.workschedule.ui.train_edit.TrainEditFragment.Companion.TRAIN_ID
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class TrainsFragment : Fragment() {
    private val trainsViewModel: TrainsViewModel by viewModel()
    private var _binding: FragmentTrainsBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentTrainsBinding? = null")
    private val adapter: TrainsFragmentAdapter by lazy { TrainsFragmentAdapter(requireActivity().menuInflater) }
    private lateinit var buttonNewTrain: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainsBinding.inflate(inflater, container, false)
        buttonNewTrain = (activity as AppCompatActivity).findViewById(R.id.toolbar_add_new_train)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.trainsFragmentRecyclerView)
        initView()
    }

    override fun onStart() {
        buttonNewTrain.visibility = View.VISIBLE
        super.onStart()
    }

    private fun initView() {
        binding.trainsFragmentRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            trainsViewModel.trains
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
        trainsViewModel.getTrains()

        buttonNewTrain.setOnClickListener {
            findNavController().navigate(R.id.nav_train_edit)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update_train_from_context -> {
                val bundle = bundleOf(TRAIN_ID to adapter.clickedId)
                findNavController().navigate(R.id.nav_train_edit, bundle)
            }
            R.id.action_delete_train_from_context -> {
                adapter.removeItem()
                trainsViewModel.deleteTrain(adapter.clickedId)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onStop() {
        buttonNewTrain.visibility = View.GONE
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
