package com.example.workschedule.ui.worker_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentDriverEditItemBinding
import com.example.workschedule.domain.domainpersonmodel.DomainPathDirectionModel

class WorkerEditAdapter : ListAdapter<DomainPathDirectionModel, WorkerEditAdapter.WorkerEditViewHolder>(WorkerEditCallback) {

    inner class WorkerEditViewHolder(private val binding: FragmentDriverEditItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun show(direction: DomainPathDirectionModel) = with(binding) {
            editDriverFragmentRecyclerItemDestination.text = direction.destination
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerEditViewHolder =
        WorkerEditViewHolder(
            FragmentDriverEditItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WorkerEditViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    companion object WorkerEditCallback : DiffUtil.ItemCallback<DomainPathDirectionModel>() {
        override fun areItemsTheSame(oldItem: DomainPathDirectionModel, newItem: DomainPathDirectionModel) = oldItem == newItem
        override fun areContentsTheSame(oldItem: DomainPathDirectionModel, newItem: DomainPathDirectionModel) = oldItem == newItem
    }

}