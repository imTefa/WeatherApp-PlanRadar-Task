package com.planradar.task.features.addcity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.planradar.task.R

class AddCityAdapter(private var list: List<SearchCityUiState>) :
    RecyclerView.Adapter<AddCityAdapter.Holder>() {


    inner class Holder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val txt = view.findViewById<TextView>(R.id.txt)

        fun bind() {
            val city = list[adapterPosition]
            txt.text = "$city"

            view.setOnClickListener { city.onCitySelected() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList: List<SearchCityUiState>) {
        list = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}