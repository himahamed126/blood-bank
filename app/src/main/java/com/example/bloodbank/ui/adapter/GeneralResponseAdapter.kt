package com.example.bloodbank.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bloodbank.R
import com.example.bloodbank.data.model.general.GeneralData

class GeneralResponseAdapter(context: Context?) : BaseAdapter() {
    private var generalList: MutableList<GeneralData>? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var selectedId = 0
    fun setData(data: MutableList<GeneralData>, hint: String?) {
        generalList = mutableListOf()
        (generalList)!!.add(GeneralData(0, hint!!))
        (generalList)!!.addAll(data)
    }

    override fun getCount(): Int {
        return generalList!!.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.item_spinner, null)
        val textView = view.findViewById<TextView>(R.id.sp_text)
        textView.text = generalList!![position].name
        selectedId = generalList!![position].id
        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = inflater.inflate(R.layout.item_spinner, null)
        val text = view.findViewById<TextView>(R.id.sp_text)
        text.text = generalList!![position].name
        text.setTextColor(Color.parseColor("#9A0B0B"))
        selectedId = generalList!![position].id
        return view
    }
}

