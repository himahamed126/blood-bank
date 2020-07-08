package com.example.bloodbank.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.bloodbank.R

class SliderPagerAdapter(private val activity: Activity) : PagerAdapter() {

    private val layout = intArrayOf(R.layout.slider_1, R.layout.slider_2, R.layout.slider_3)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layout[position], container, false)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return layout.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

}