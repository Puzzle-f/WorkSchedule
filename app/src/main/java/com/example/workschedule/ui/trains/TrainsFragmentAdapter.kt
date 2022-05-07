package com.example.workschedule.ui.trains

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.domain.models.Train
import com.example.workschedule.databinding.FragmentTrainsItemBinding

class TrainsFragmentAdapter :
    ListAdapter<Train, TrainsFragmentAdapter.MainViewHolder>(TrainCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(
            FragmentTrainsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class MainViewHolder(private val binding: FragmentTrainsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun show(train: Train) = with(binding) {
            trainsFragmentRecyclerItemNumber.text = train.number.toString()
            trainsFragmentRecyclerItemDestination.text = train.direction
        }
    }

    companion object TrainCallback : DiffUtil.ItemCallback<Train>() {
        override fun areItemsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
    }
}