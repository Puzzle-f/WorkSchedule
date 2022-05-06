package com.example.workschedule.ui.drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentDriversItemBinding
import com.example.workschedule.domain.models.DomainDriverModel

class DriversAdapter :
    ListAdapter<DomainDriverModel, DriversAdapter.DriversViewHolder>(DriversCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DriversViewHolder(
            FragmentDriversItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class DriversViewHolder(private val binding: FragmentDriversItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun show(driver: DomainDriverModel) = with(binding) {
            driversFragmentRecyclerItemName.text = driver.name
            driversFragmentRecyclerItemHours.text = driver.workedTime.toString()
            driversFragmentRecyclerItemSurName.text = getInitials(driver.surname)
            driversFragmentRecyclerItemPatronymic.text = getInitials(driver.patronymic)
        }
    }

    private fun getInitials(name: String) = name.substring(0, 1) + "."

    companion object DriversCallback : DiffUtil.ItemCallback<DomainDriverModel>() {
        override fun areItemsTheSame(oldItem: DomainDriverModel, newItem: DomainDriverModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: DomainDriverModel, newItem: DomainDriverModel) =
            oldItem == newItem
    }
}
