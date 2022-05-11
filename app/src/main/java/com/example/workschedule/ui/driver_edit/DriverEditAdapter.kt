package com.example.workschedule.ui.driver_edit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentDriverEditItemBinding
import com.example.workschedule.domain.models.Train

class DriverEditAdapter :
    ListAdapter<Train, DriverEditAdapter.WorkerEditViewHolder>(WorkerEditCallback) {

    private var accessIdList: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerEditViewHolder =
        WorkerEditViewHolder(
            FragmentDriverEditItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: WorkerEditViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAccessList(accessList: List<Int>) {
        accessIdList = accessList as MutableList<Int>
        notifyDataSetChanged()
    }

    fun getAccessList(): List<Int> = accessIdList

    inner class WorkerEditViewHolder(private val binding: FragmentDriverEditItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(train: Train) = with(binding) {
            editDriverFragmentRecyclerItemDestination.text = train.direction
            if (train.number in accessIdList) editDriverFragmentRecyclerItemSwitch.isChecked = true
            editDriverFragmentRecyclerItemSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && train.number !in accessIdList) accessIdList.add(train.number)
            }
        }
    }

    companion object WorkerEditCallback : DiffUtil.ItemCallback<Train>() {
        override fun areItemsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
    }
}