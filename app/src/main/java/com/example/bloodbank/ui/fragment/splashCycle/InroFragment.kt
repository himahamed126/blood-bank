package com.example.bloodbank.ui.fragment.splashCycle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.ui.adapter.SliderPagerAdapter
import com.example.bloodbank.databinding.FragmentInroBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.FIRST_LAUNCH
import com.example.bloodbank.ui.activity.AuthActivity
import com.example.bloodbank.ui.fragment.BaseFragment

class InroFragment : BaseFragment() {

    private var layout = intArrayOf(R.layout.slider_1, R.layout.slider_2, R.layout.slider_3)
    lateinit var binding: FragmentInroBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_inro, container)

        val sliderPagerAdapter = SliderPagerAdapter(activity as AppCompatActivity)
        binding.fragmentInroVpSlider.adapter = sliderPagerAdapter
        binding.fragmentInroVpSlider.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                changeDotImage(position)
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        lunchHomeScreen()
        return binding.root
    }

    private fun Int.getItem(): Int {
        return binding.fragmentInroVpSlider.currentItem + this
    }

    private fun changeDotImage(pos: Int) {
        when (pos) {
            0 -> {
                binding.fragmentInroCircle1.setImageResource(R.drawable.sh_cir_orange)
                binding.fragmentInroCircle2.setImageResource(R.drawable.sh_cir_red)
                binding.fragmentInroCircle3.setImageResource(R.drawable.sh_cir_red)
            }
            1 -> {
                binding.fragmentInroCircle1.setImageResource(R.drawable.sh_cir_red)
                binding.fragmentInroCircle2.setImageResource(R.drawable.sh_cir_orange)
                binding.fragmentInroCircle3.setImageResource(R.drawable.sh_cir_red)
            }
            2 -> {
                binding.fragmentInroCircle1.setImageResource(R.drawable.sh_cir_red)
                binding.fragmentInroCircle2.setImageResource(R.drawable.sh_cir_red)
                binding.fragmentInroCircle3.setImageResource(R.drawable.sh_cir_orange)
            }
        }
    }

    private fun lunchHomeScreen() {
        binding.fragmentInroBtnNext.setOnClickListener {
            val current = (+1).getItem()
            if (current < this.layout.size) {
                binding.fragmentInroVpSlider.currentItem = current
            } else {
                SharedPreferencesManger.SaveData(this.activity!!, FIRST_LAUNCH, true)
                val intent = Intent(activity, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }
}