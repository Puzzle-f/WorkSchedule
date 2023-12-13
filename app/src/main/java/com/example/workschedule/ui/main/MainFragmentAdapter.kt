package com.example.workschedule.ui.main

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.utils.toLocalDateTime
import kotlinx.coroutines.flow.StateFlow
import java.time.format.DateTimeFormatter

class MainFragmentAdapter(
    private val menuInflater: MenuInflater,
    private val borderHorizon: StateFlow<MainFragmentData?>
) :
    ListAdapter<MainFragmentData, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

    var clickedTrainRunId = -1
     var itemPosition = -1

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
                currentList[position].data.toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd.MM"))
            mainFragmentRecyclerItemTime.text =
                currentList[position].time
            mainFragmentRecyclerItemTrain.text =
                currentList[position].trainNumber.toString()
            directionTv.text = currentList[position].direction

            mainFragmentRecyclerItemDriver.text = currentList[position].driver
            mainFragmentRecyclerItemTravelTimeTo.text =
                currentList[position].roadTime
            mainFragmentRecyclerItemWorkTime.text =
                currentList[position].workTime
            mainFragmentRecyclerItemCountNight.text =
                currentList[position].countNight.toString()

            if (currentList[position] == borderHorizon.value) {
                lineTop.visibility = View.VISIBLE
                lineTop.setBackgroundResource(R.color.red)
            } else {
                lineTop.visibility = View.INVISIBLE
                lineTop.setBackgroundResource(0x00000000)
            }

            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedTrainRunId = currentList[adapterPosition].id
                false
            }

            if (mainFragmentRecyclerItemDriver.text == "" || mainFragmentRecyclerItemDriver.text == "-") {
                layoutContainer.setBackgroundResource(R.color.red)
            } else if (currentList[adapterPosition].isEditManually) {
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
        override fun areItemsTheSame(oldItem: MainFragmentData, newItem: MainFragmentData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MainFragmentData, newItem: MainFragmentData) =
            oldItem == newItem
    }
}