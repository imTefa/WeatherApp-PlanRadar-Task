package com.planradar.task.features.cities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.planradar.task.databinding.ListItemCityBinding

class CitiesAdapter(private var list: List<CityUiState>) :
    RecyclerView.Adapter<CitiesAdapter.Holder>() {


    inner class Holder(private val binding: ListItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.txtCityName.text = list[adapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemCityBinding.inflate(
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
    fun updateList(updatedList: List<CityUiState>){
        list = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}