package com.example.workschedule.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.FragmentMainItemBinding
import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel
import java.util.*

class MainFragmentAdapter :
    ListAdapter<DomainPersonModel, MainFragmentAdapter.MainViewHolder>(DomainPersonModelCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            FragmentMainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class MainViewHolder(private val vb: FragmentMainItemBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: DomainPersonModel) {
            vb.mainFragmentRecyclerItemDate.text =
                "${GregorianCalendar().get(Calendar.DAY_OF_MONTH)}." +
                        "${GregorianCalendar().get(Calendar.MONTH)}." +
                        "${GregorianCalendar().get(Calendar.YEAR)}"
            vb.mainFragmentRecyclerItemTime.text =
                "${GregorianCalendar().get(Calendar.HOUR_OF_DAY)}: " +
                        "${GregorianCalendar().get(Calendar.MINUTE)}"
            vb.mainFragmentRecyclerItemTrain.text = "${(100..999).random()} Moscow"
            vb.mainFragmentRecyclerItemTravelTimeTo.text = "${(0..24).random()}:${(0..60).random()}"
            vb.mainFragmentRecyclerItemRestTime.text = "${(1..24).random()}"
            vb.mainFragmentRecyclerItemTravelFrom.text = "${(0..24).random()}:${(0..60).random()}"
        }
    }

    companion object DomainPersonModelCallback : DiffUtil.ItemCallback<DomainPersonModel>() {
        override fun areItemsTheSame(
            oldItem: DomainPersonModel,
            newItem: DomainPersonModel,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DomainPersonModel,
            newItem: DomainPersonModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}