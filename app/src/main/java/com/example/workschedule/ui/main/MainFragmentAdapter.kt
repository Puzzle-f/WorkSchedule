package com.example.workschedule.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentMainItemBinding

class MainFragmentAdapter :
    ListAdapter<TestModelForMainAdapter, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            FragmentMainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class MainViewHolder(private val vb: FragmentMainItemBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: TestModelForMainAdapter) {
            vb.mainFragmentRecyclerItemDate.text = model.date
            vb.mainFragmentRecyclerItemTime.text = model.time
            vb.mainFragmentRecyclerItemTrain.text = model.train
            vb.mainFragmentRecyclerItemTravelTimeTo.text = model.travelTimeTo
            vb.mainFragmentRecyclerItemRestTime.text = model.restTime
            vb.mainFragmentRecyclerItemTravelFrom.text = model.travelFrom
        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<TestModelForMainAdapter>() {
        override fun areItemsTheSame(
            oldItem: TestModelForMainAdapter,
            newItem: TestModelForMainAdapter,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TestModelForMainAdapter,
            newItem: TestModelForMainAdapter,
        ): Boolean {
            return oldItem == newItem
        }
    }
}