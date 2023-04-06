package com.example.workschedule.ui.driver_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentDriverEditItemBinding
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Permission

class DriverEditAdapter :
    ListAdapter<Direction, DriverEditAdapter.WorkerEditViewHolder>(WorkerEditCallback) {

    var permissionListFromAdapter: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerEditViewHolder =
        WorkerEditViewHolder(
            FragmentDriverEditItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: WorkerEditViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun permissionList() {
        val b = Bundle().getIntArray(KEY_PERMISSION)
        if(b!=null){
            permissionListFromAdapter = b.toMutableList()
        }
    }

    inner class WorkerEditViewHolder(private val binding: FragmentDriverEditItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(direction: Direction) = with(binding) {
            editDriverFragmentRecyclerItemDestination.text = direction.name
            if (direction.id in permissionListFromAdapter) editDriverFragmentRecyclerItemSwitch.isChecked = true
            editDriverFragmentRecyclerItemSwitch.setOnClickListener {
                if (editDriverFragmentRecyclerItemSwitch.isChecked) {
                    permissionListFromAdapter.add(direction.id)
                }
                if (!editDriverFragmentRecyclerItemSwitch.isChecked) {
                    permissionListFromAdapter.remove(direction.id)
                }
            }
        }
    }

    companion object WorkerEditCallback : DiffUtil.ItemCallback<Direction>() {
        override fun areItemsTheSame(oldItem: Direction, newItem: Direction) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Direction, newItem: Direction) = oldItem == newItem
    }
}
val KEY_PERMISSION = "KEY_PERMISSION"