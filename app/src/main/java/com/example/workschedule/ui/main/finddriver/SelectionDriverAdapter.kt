package com.example.workschedule.ui.main.finddriver

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentSelectionDriverBinding
import com.example.workschedule.databinding.FragmentSelectionDriverItemBinding
import com.example.workschedule.utils.FIO
import java.sql.Driver

class SelectionDriverAdapter(
    private val menuInflater: MenuInflater
) : ListAdapter<com.example.workschedule.domain.models.Driver, SelectionDriverAdapter.SelectionDriverViewHolder>(SelectionDriverCallback) {

    var clickedDriverId = -1
    private var itemPosition = -1

    inner class SelectionDriverViewHolder(private val binding: FragmentSelectionDriverItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        @SuppressLint("SetTextI19n")
        fun bind(position: Int) = with(binding) {
            selectionDriversRecyclerItemRestTime.text =
                currentList[position].personalNumber .toString()
            selectionDriversRecyclerItemDriverFIO.text = currentList[position].FIO
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedDriverId = currentList[adapterPosition].id
                false
            }
        }

        override fun onCreateContextMenu(
            p0: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectionDriverViewHolder(
            FragmentSelectionDriverItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SelectionDriverViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object SelectionDriverCallback: DiffUtil.ItemCallback<com.example.workschedule.domain.models.Driver>(){
        override fun areItemsTheSame(oldItem: com.example.workschedule.domain.models.Driver, newItem: com.example.workschedule.domain.models.Driver) = oldItem == newItem
        override fun areContentsTheSame(oldItem: com.example.workschedule.domain.models.Driver, newItem: com.example.workschedule.domain.models.Driver) = oldItem == newItem
    }

//    companion object SelectionDriverCallback : DiffUtil.ItemCallback<Driver>() {
//        override fun areItemsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
//        override fun areContentsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
//    }
}