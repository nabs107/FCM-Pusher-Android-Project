package com.fcmpusher.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fcmpusher.R
import com.fcmpusher.databinding.RowAppItemBinding
import com.fcmpusher.interfaces.ListItemCallback
import com.fcmpusher.model.App

class AppListAdapter(private val list: List<App>, val clickListener: (App) -> Unit) :
    RecyclerView.Adapter<AppListAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val rowAppItemBinding = DataBindingUtil.inflate<RowAppItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_app_item,
            parent,
            false
        )
        return BindingHolder(rowAppItemBinding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val app: App = list[position]
        holder.binding.tvAppName.setOnClickListener { clickListener.invoke(app) }
        return holder.bind(app)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BindingHolder(val binding: RowAppItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(app: App) {
            binding.tvAppName.text = app.appName
        }
    }

}