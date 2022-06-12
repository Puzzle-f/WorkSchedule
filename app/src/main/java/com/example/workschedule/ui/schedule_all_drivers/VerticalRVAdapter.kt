package com.example.workschedule.ui.schedule_all_drivers

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.R
import com.example.workschedule.databinding.VerticalItemBinding
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.ui.schedule_all_drivers.adapters_model.HorizontalRVModel
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class VerticalRVAdapter(
    private val driver: Driver,
    private val trainsRuns: List<HorizontalRVModel>,
) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.vertical_item)
        .build()
) {
    override fun getContentItemsTotal() = 1

    inner class ItemViewHolder(private val bindingItem: VerticalItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        private val horizontalRecyclerView: RecyclerView
        private val horizontalAdapter: HorizontalRVAdapter

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
            horizontalAdapter = HorizontalRVAdapter()
            horizontalRecyclerView.adapter = horizontalAdapter
        }

        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            bindingItem.subcategoryText.text = driver.surname
            horizontalAdapter.submitList(trainsRuns)
            horizontalAdapter.setRowIndex(position)
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(VerticalItemBinding.bind(view))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(position)
    }
}