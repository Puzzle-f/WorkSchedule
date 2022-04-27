package com.example.workschedule.ui.workers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.data.entities.Driver
import com.example.workschedule.databinding.FragmentDriversItemBinding
import com.example.workschedule.utils.driverList

class WorkersAdapter : RecyclerView.Adapter<WorkersAdapter.ViewHolder>() {
    private var usersList: MutableList<Driver> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun load(driversList: List<Driver>) {
        this.usersList = driversList as MutableList<Driver>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentDriversItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class ViewHolder(private val binding: FragmentDriversItemBinding) : RecyclerView.ViewHolder(binding.root) {

//        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            driversFragmentRecyclerItemName.text = driverList[position].name
            driversFragmentRecyclerItemHours.text = driverList[position].workedTime.toInt().toString()
        }
    }
}
