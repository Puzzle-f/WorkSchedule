package com.example.workschedule.ui.finddriver

import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentSelectionDriverItemBinding
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SelectionDriverAdapter(
    private val menuInflater: MenuInflater,
    private var onListItemClickListener: OnListItemClickListener
) : ListAdapter<SelectionDriverItemData, SelectionDriverAdapter.SelectionDriverViewHolder>(SelectionDriverCallback) {

    var clickedDriverId = -1
    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectionDriverViewHolder(
            FragmentSelectionDriverItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SelectionDriverViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class SelectionDriverViewHolder(private val binding: FragmentSelectionDriverItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            selectionDriversRecyclerItemDriverFIO.text =
                currentList[position].driverName
//            selectionDriversRecyclerItemDriverFIO.text = currentList[position].restTime
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                clickedDriverId = currentList[adapterPosition].id
                false
            }
            
            itemView.setOnClickListener {
                itemPosition = adapterPosition
                clickedDriverId = currentList[adapterPosition].id
                updateTrainRun(clickedDriverId)
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

    fun updateTrainRun(driverId: Int){
        onListItemClickListener.onItemClick(driverId)
    }

    companion object SelectionDriverCallback: DiffUtil.ItemCallback<SelectionDriverItemData>(){
        override fun areItemsTheSame(oldItem: SelectionDriverItemData, newItem: SelectionDriverItemData) = oldItem == newItem
        override fun areContentsTheSame(oldItem: SelectionDriverItemData, newItem: SelectionDriverItemData) = oldItem == newItem
    }
    interface OnListItemClickListener {
        fun onItemClick(driverId: Int)
    }
}