package com.example.workschedule.ui.schedule_all_drivers

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.VerticalItemBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.schedule_all_drivers.model.Horizontal_RVModel
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList

class Vertical_RVAdapter(
    private val driver: Driver,
    private val trains: List<Horizontal_RVModel>,
//    private val trains: StateFlow <List<Horizontal_RVModel>>
) :
    Section(
        SectionParameters.builder()
            .itemResourceId(R.layout.vertical_item)
//                можно добавить заголовок
//            .headerResourceId(R.layout.vertical_header)
            .build()
    ) {

    override fun getContentItemsTotal(): Int {
        Log.d("", "trains.value.size = ${trains.size}")
        return trains.size
    }

    class ItemViewHolder(val bindingItem: VerticalItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        private val horizontalRecyclerView: RecyclerView
        val horizontalAdapter: Horizontal_RVAdapter

        init {
            val context = itemView.context
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_rv)
            horizontalRecyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            horizontalRecyclerView?.addItemDecoration(
                EqualSpacingItemDecoration(
                    8,
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            horizontalAdapter = Horizontal_RVAdapter(context)
            horizontalRecyclerView.adapter = horizontalAdapter
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(VerticalItemBinding.bind(view))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemViewHolder
        itemHolder.bindingItem.subcategoryText.text = driver.surname
        itemHolder.horizontalAdapter.setHorizontalData(trains)
        itemHolder.horizontalAdapter.setRowIndex(position)

    }
}