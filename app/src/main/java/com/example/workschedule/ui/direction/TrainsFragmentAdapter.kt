package com.example.workschedule.ui.direction

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.FragmentDirectionItemBinding
import com.example.workschedule.domain.models.Direction

class TrainsFragmentAdapter(
    private val menuInflater: MenuInflater
) :
    ListAdapter<Direction, TrainsFragmentAdapter.MainViewHolder>(TrainCallback) {

    var clickedId = -1
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(
            FragmentDirectionItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: FragmentDirectionItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            trainsFragmentRecyclerItemDestination.text = currentList[position].name
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

    companion object TrainCallback : DiffUtil.ItemCallback<Direction>() {
        override fun areItemsTheSame(oldItem: Direction, newItem: Direction) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Direction, newItem: Direction) = oldItem == newItem
    }
}