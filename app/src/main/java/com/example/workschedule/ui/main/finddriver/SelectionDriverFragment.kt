package com.example.workschedule.ui.main.finddriver

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentSelectionDriverBinding
import com.example.workschedule.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class SelectionDriverFragment :
    BaseFragment<FragmentSelectionDriverBinding>(FragmentSelectionDriverBinding::inflate) {

    private val selectionDriverViewModel: SelectionDriverViewModel by viewModel()
    private val adapter by lazy { SelectionDriverAdapter(requireActivity().menuInflater) }
    private lateinit var closeButton: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        closeButton = (activity as AppCompatActivity).findViewById(R.id.close_button)
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(binding.selectionDriverFragmentRecyclerView)
    }

    override fun onStart() {
        closeButton.visibility = View.VISIBLE
        super.onStart()
    }

    override fun readArguments(bundle: Bundle) {
        TODO("Not yet implemented")
    }

    override fun initView() {
        closeButton.setOnClickListener {
            findNavController().navigate(R.id.nav_main)
        }
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }

    override fun initObservers() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        closeButton.visibility = View.GONE
        super.onStop()
    }


}