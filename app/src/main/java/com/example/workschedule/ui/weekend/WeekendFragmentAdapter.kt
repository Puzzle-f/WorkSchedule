package com.example.workschedule.ui.weekend

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentWeekendDistractionItemBinding
import com.example.workschedule.databinding.FragmentWeekendItemBinding
import com.example.workschedule.domain.models.Weekend
import com.example.workschedule.utils.toLocalDateTime
import java.time.format.DateTimeFormatter

class WeekendFragmentAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<Weekend, WeekendFragmentAdapter.WeekendViewHolder>(WeekendCallback) {

    var clickedWeekendDriverId = -1
    var clickedWeekendDate = 0L
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeekendViewHolder(
            FragmentWeekendItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: WeekendFragmentAdapter.WeekendViewHolder, position: Int) {
        holder.bind(position)
    }

    fun removeItem() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.removeAt(itemPosition)
        submitList(currentListMutable)
    }

    fun removeAll() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.clear()
        submitList(currentListMutable)
    }

    inner class WeekendViewHolder(private val binding: FragmentWeekendItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            weekendFragmentRecyclerItemDestination.text =
                currentList[position].date.toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("dd.MM.yy"))
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedWeekendDriverId = currentList[adapterPosition].driverId
                clickedWeekendDate = currentList[adapterPosition].date
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_weekend_menu, menu)
        }
    }

    companion object WeekendCallback : DiffUtil.ItemCallback<Weekend>() {
        override fun areItemsTheSame(oldItem: Weekend, newItem: Weekend) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Weekend, newItem: Weekend) = oldItem == newItem
    }
}