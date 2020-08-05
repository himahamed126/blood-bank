package com.example.bloodbank.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.databinding.ItemCheckBoxBinding
import com.example.bloodbank.extensions.inflateWithBinding

class CheckBoxAdapter(
        private val generalDataList: MutableList<GeneralData>,
        private val oldList: MutableList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var newIds: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxViewHolder {
        val viewBinding = parent.inflateWithBinding<ItemCheckBoxBinding>(R.layout.item_check_box)
        return CheckBoxViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CheckBoxViewHolder).bind()
    }

    override fun getItemCount(): Int {
        return generalDataList.size
    }

    inner class CheckBoxViewHolder(var binding: ItemCheckBoxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val data = generalDataList[adapterPosition]
                if (oldList.contains(data.id.toString())) {
                    itemCheckBoxCh.isChecked = true
                    newIds.add(data.id)
                } else {
                    itemCheckBoxCh.isChecked = false
                }
                this.item = data
                executePendingBindings()

                binding.itemCheckBoxCh.setOnCheckedChangeListener { _, isChecked ->
                    if (!isChecked) {
                        for (i in newIds.indices) {
                            if (newIds[i] == data.id) {
                                newIds.removeAt(i)
                                break
                            }
                        }
                    } else {
                        newIds.add(data.id)
                    }
                }

            }
        }
    }
}