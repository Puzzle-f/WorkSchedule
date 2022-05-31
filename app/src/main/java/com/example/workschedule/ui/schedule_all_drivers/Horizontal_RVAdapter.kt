package com.example.workschedule.ui.schedule_all_drivers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workschedule.databinding.HorizontalItemBinding
import com.example.workschedule.ui.schedule_all_drivers.model.Horizontal_RVModel

class Horizontal_RVAdapter(private var mContext: Context) :
    ListAdapter<Horizontal_RVModel, Horizontal_RVAdapter.ItemViewHolder>(HorizontalCallback)
//    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    var itemsList: List<Horizontal_RVModel> = ArrayList()
    private var mRowIndex = -1

    fun setHorizontalData(data: List<Horizontal_RVModel>) {
        if (itemsList != data) {
            itemsList = data
            notifyDataSetChanged()
        }
    }

    fun setRowIndex(index: Int) {
        mRowIndex = index
    }

    class ItemViewHolder(val binding: HorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            HorizontalItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.itemDate.text = itemsList[position].data
        holder.binding.itemTrainNumber.text = itemsList[position].trainNumber
        holder.binding.itemHorizontalLayout.setOnClickListener {
//            val intent = Intent(mContext, DetailsActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString("detail_color", itemsList[position].color)
//            bundle.putString("detail_name", itemsList[position].name)
//            intent.putExtras(bundle)
//            mContext.startActivity(intent)
            Toast.makeText(
                mContext, itemsList[position].data, Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object HorizontalCallback : DiffUtil.ItemCallback<Horizontal_RVModel>() {
        override fun areItemsTheSame(oldItem: Horizontal_RVModel, newItem: Horizontal_RVModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Horizontal_RVModel, newItem: Horizontal_RVModel) =
            oldItem == newItem
    }


}