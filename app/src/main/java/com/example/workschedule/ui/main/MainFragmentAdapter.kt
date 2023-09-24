package com.example.workschedule.ui.main

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toTimeString
import java.time.format.DateTimeFormatter

class MainFragmentAdapter(
    private val menuInflater: MenuInflater,
) :
    ListAdapter<MainFragmentData, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

    var clickedTrainRunId = -1
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(
            FragmentMainItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(position)
    }

    fun removeItem() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.removeAt(itemPosition)
        submitList(currentListMutable)
    }

    fun removeAllItems() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.clear()
        submitList(currentListMutable)
    }

    inner class MainViewHolder(private val binding: FragmentMainItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            mainFragmentRecyclerItemDate.text =
                currentList[position].data
            mainFragmentRecyclerItemTime.text =
                currentList[position].time
            mainFragmentRecyclerItemTrain.text =
                with(currentList[position]) { "$trainNumber $direction" }
            mainFragmentRecyclerItemDriver.text = currentList[position].driver
            mainFragmentRecyclerItemTravelTimeTo.text =
                currentList[position].roadTime
            mainFragmentRecyclerItemWorkTime.text =
                currentList[position].workTime
            mainFragmentRecyclerItemCountNight.text =
                currentList[position].countNight.toString()
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedTrainRunId = currentList[adapterPosition].id
                false
            }

            if (mainFragmentRecyclerItemDriver.text == "" || mainFragmentRecyclerItemDriver.text == "0") {
                layoutContainer.setBackgroundResource(R.color.red)
            }
            else if (currentList[adapterPosition].isEditManually) {
                    layoutContainer.setBackgroundResource(R.color.background_is_edit_manually)
                } else layoutContainer.setBackgroundResource(R.color.on_primary)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_main, menu)
        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<MainFragmentData>() {
        override fun areItemsTheSame(oldItem: MainFragmentData, newItem: MainFragmentData) = oldItem == newItem
        override fun areContentsTheSame(oldItem: MainFragmentData, newItem: MainFragmentData) = oldItem == newItem
    }
}