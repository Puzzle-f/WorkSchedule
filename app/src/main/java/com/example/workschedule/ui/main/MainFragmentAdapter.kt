package com.example.workschedule.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.models.TrainRun

class MainFragmentAdapter :
    ListAdapter<TrainRun, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

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

        fun show(model: TrainRun) {

        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<TrainRun>() {
        override fun areItemsTheSame(
            oldItem: TrainRun,
            newItem: TrainRun,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TrainRun,
            newItem: TrainRun,
        ): Boolean {
            return oldItem == newItem
        }
    }
}