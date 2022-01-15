package com.planradar.task.features.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.planradar.task.databinding.ListItemHistoryRecordBinding

class HistoryAdapter(private var list: List<RecordUiState>) :
    RecyclerView.Adapter<HistoryAdapter.Holder>() {


    inner class Holder(private val binding: ListItemHistoryRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val record = list[adapterPosition]
            binding.record = record
            binding.imageArrow.setOnClickListener { record.onArrowClicked() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemHistoryRecordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList: List<RecordUiState>) {
        list = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}