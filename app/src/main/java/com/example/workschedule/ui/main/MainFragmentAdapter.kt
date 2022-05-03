package com.example.workschedule.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.models.DomainTrainRunModel

class MainFragmentAdapter :
    ListAdapter<DomainTrainRunModel, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

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

        fun show(model: DomainTrainRunModel) {

        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<DomainTrainRunModel>() {
        override fun areItemsTheSame(
            oldItem: DomainTrainRunModel,
            newItem: DomainTrainRunModel,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DomainTrainRunModel,
            newItem: DomainTrainRunModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}