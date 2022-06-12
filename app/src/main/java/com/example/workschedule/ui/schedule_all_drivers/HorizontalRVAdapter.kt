package com.example.workschedule.ui.schedule_all_drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.HorizontalItemBinding
import com.example.workschedule.ui.schedule_all_drivers.adapters_model.HorizontalRVModel

class HorizontalRVAdapter :
    ListAdapter<HorizontalRVModel, HorizontalRVAdapter.ItemViewHolder>(HorizontalCallback) {
    private var mRowIndex = -1

    fun setRowIndex(index: Int) {
        mRowIndex = index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            HorizontalItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ItemViewHolder(val binding: HorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.itemDate.text = currentList[position].data
            binding.itemTrainNumber.text = currentList[position].trainNumber
        }
    }

    companion object HorizontalCallback : DiffUtil.ItemCallback<HorizontalRVModel>() {
        override fun areItemsTheSame(oldItem: HorizontalRVModel, newItem: HorizontalRVModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HorizontalRVModel, newItem: HorizontalRVModel) =
            oldItem == newItem
    }
}