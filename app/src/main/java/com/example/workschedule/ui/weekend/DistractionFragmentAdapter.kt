package com.example.workschedule.ui.weekend

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentWeekendDistractionItemBinding
import com.example.workschedule.databinding.FragmentWeekendItemBinding
import com.example.workschedule.domain.models.Distraction
import com.example.workschedule.utils.toLocalDateTime
import java.time.format.DateTimeFormatter

class DistractionFragmentAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<Distraction, DistractionFragmentAdapter.DistractionViewHolder>(DistractionCallback) {

    var clickedDistractionDriverId = -1
    var clickedDistractionDate = 0L
    private var itemDistractionPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DistractionViewHolder(
            FragmentWeekendDistractionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: DistractionFragmentAdapter.DistractionViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    fun removeItem() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.removeAt(itemDistractionPosition)
        submitList(currentListMutable)
    }

    fun removeAll() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.clear()
        submitList(currentListMutable)
    }

    inner class DistractionViewHolder(private val binding: FragmentWeekendDistractionItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            if(currentList[position].isDistracted){
                weekendFragmentRecyclerItemDistraction.text =
                    currentList[position].date.toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("с: dd.MM.yy")
                    )
            } else {
                weekendFragmentRecyclerItemDistraction.text =
                    currentList[position].date.toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("по: dd.MM.yy")
                    )
            }

            itemView.setOnLongClickListener {
                itemDistractionPosition = adapterPosition
                clickedDistractionDriverId = currentList[adapterPosition].driverId
                clickedDistractionDate = currentList[adapterPosition].date
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_weekend_menu, menu)
        }
    }

    companion object DistractionCallback : DiffUtil.ItemCallback<Distraction>() {
        override fun areItemsTheSame(oldItem: Distraction, newItem: Distraction) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Distraction, newItem: Distraction) =
            oldItem == newItem
    }
}