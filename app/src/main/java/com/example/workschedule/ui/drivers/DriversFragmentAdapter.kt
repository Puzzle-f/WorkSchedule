package com.example.workschedule.ui.drivers

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDriversItemBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.utils.toTimeString

class DriversAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<Driver, DriversAdapter.DriversViewHolder>(DriversCallback) {

    var clickedDriverId = -1
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DriversViewHolder(
            FragmentDriversItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        holder.bind(position)
    }

    fun removeItem() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.removeAt(itemPosition)
        submitList(currentListMutable)
    }

    inner class DriversViewHolder(private val binding: FragmentDriversItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            driversFragmentRecyclerItemSurName.text = currentList[position].surname
            driversFragmentRecyclerItemName.text =
                "${currentList[position].name.first()}."
            driversFragmentRecyclerItemPatronymic.text =
                "${currentList[position].patronymic.first()}."
            driversFragmentRecyclerItemHours.text = currentList[position].workedTime.toTimeString
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedDriverId = currentList[adapterPosition].id
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_drivers, menu)
        }
    }

    companion object DriversCallback : DiffUtil.ItemCallback<Driver>() {
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
    }
}