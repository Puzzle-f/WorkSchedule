package com.example.workschedule.ui.main

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.toTimeString
import java.time.format.DateTimeFormatter

class MainFragmentAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<TrainRun, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

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

    fun removeAllItems(){
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
                currentList[position].startTime.format(DateTimeFormatter.ofPattern("dd.MM.y"))
            mainFragmentRecyclerItemTime.text =
                currentList[position].startTime.format(DateTimeFormatter.ofPattern(" HH:mm"))
            mainFragmentRecyclerItemTrain.text =
                with(currentList[position]) { "$trainNumber $trainDirection" }
            mainFragmentRecyclerItemDriver.text = currentList[position].driverName
            mainFragmentRecyclerItemTravelTimeTo.text =
                currentList[position].travelTime.toTimeString
            mainFragmentRecyclerItemRestTime.text =
                currentList[position].travelRestTime.toTimeString
            mainFragmentRecyclerItemTravelFrom.text =
                currentList[position].backTravelTime.toTimeString
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedTrainRunId = currentList[adapterPosition].id
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_main, menu)
        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<TrainRun>() {
        override fun areItemsTheSame(oldItem: TrainRun, newItem: TrainRun) = oldItem == newItem
        override fun areContentsTheSame(oldItem: TrainRun, newItem: TrainRun) = oldItem == newItem
    }
}