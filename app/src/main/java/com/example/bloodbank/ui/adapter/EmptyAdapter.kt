package com.example.bloodbank.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.databinding.ItemAdapterBinding
import com.example.bloodbank.extensions.inflateWithBinding

class EmptyAdapter(private val list: MutableList<*>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = parent.inflateWithBinding<ItemAdapterBinding>(R.layout.item_adapter)
        return BindingViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BindingViewHolder).bind()
    }


    override fun getItemCount(): Int = list.size

    inner class BindingViewHolder(var binding: ItemAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                executePendingBindings()
            }
        }
    }
}