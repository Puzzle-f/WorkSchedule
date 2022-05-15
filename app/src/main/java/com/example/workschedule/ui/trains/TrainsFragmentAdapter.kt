package com.example.workschedule.ui.trains

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentTrainsItemBinding
import com.example.workschedule.domain.models.Train

class TrainsFragmentAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<Train, TrainsFragmentAdapter.MainViewHolder>(TrainCallback) {

    var clickedId = -1
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(
            FragmentTrainsItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: FragmentTrainsItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            trainsFragmentRecyclerItemDestination.text = currentList[position].direction
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedId = currentList[adapterPosition].id
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_trains, menu)
        }
    }

    companion object TrainCallback : DiffUtil.ItemCallback<Train>() {
        override fun areItemsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Train, newItem: Train) = oldItem == newItem
    }
}