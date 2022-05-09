package com.example.workschedule.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.toTimeString
import java.time.format.DateTimeFormatter

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

    inner class MainViewHolder(private val binding: FragmentMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun show(trainRun: TrainRun) = with(binding) {
            mainFragmentRecyclerItemDate.text =
                trainRun.startTime.format(DateTimeFormatter.ofPattern("d.MM.y"))
            mainFragmentRecyclerItemTime.text =
                trainRun.startTime.format(DateTimeFormatter.ofPattern(" H:m"))
            mainFragmentRecyclerItemTrain.text = with(trainRun) { "$trainNumber $trainDirection" }
            mainFragmentRecyclerItemDriver.text = trainRun.driverName
            mainFragmentRecyclerItemTravelTimeTo.text = trainRun.travelTime.toTimeString
            mainFragmentRecyclerItemRestTime.text = trainRun.travelRestTime.toTimeString
            mainFragmentRecyclerItemTravelFrom.text = trainRun.backTravelTime.toTimeString
        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<TrainRun>() {
        override fun areItemsTheSame(oldItem: TrainRun, newItem: TrainRun) = oldItem == newItem
        override fun areContentsTheSame(oldItem: TrainRun, newItem: TrainRun) = oldItem == newItem
    }
}