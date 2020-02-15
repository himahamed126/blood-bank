package com.example.bloodbank.ui.fragment.splashCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.SliderPagerAdapter;
import com.example.bloodbank.ui.activity.AuthActivity;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InroFragment extends BaseFragment {
    @BindView(R.id.fragment_inro_vp_slider)
    ViewPager fragmentInroVpSlider;
    @BindView(R.id.fragment_inro_circle_1)
    ImageView fragmentInroCircle1;
    @BindView(R.id.fragment_inro_circle_2)
    ImageView fragmentInroCircle2;
    @BindView(R.id.fragment_inro_circle_3)
    ImageView fragmentInroCircle3;

    private int[] layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_inro, container, false);
        ButterKnife.bind(this, view);

        layout = new int[]{R.layout.slider_1, R.layout.slider_2, R.layout.slider_3};

        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(getActivity());

        fragmentInroVpSlider.setAdapter(sliderPagerAdapter);
        fragmentInroVpSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeDotImage(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private int getItem(int i) {
        return fragmentInroVpSlider.getCurrentItem() + i;
    }

    private void lunchHomeScreen() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
    }

    private void changeDotImage(int pos) {
        switch (pos) {
            case 0:
                fragmentInroCircle1.setImageResource(R.drawable.sh_cir_orange);
                fragmentInroCircle2.setImageResource(R.drawable.sh_cir_red);
                fragmentInroCircle3.setImageResource(R.drawable.sh_cir_red);
                break;
            case 1:
                fragmentInroCircle1.setImageResource(R.drawable.sh_cir_red);
                fragmentInroCircle2.setImageResource(R.drawable.sh_cir_orange);
                fragmentInroCircle3.setImageResource(R.drawable.sh_cir_red);
                break;
            case 2:
                fragmentInroCircle1.setImageResource(R.drawable.sh_cir_red);
                fragmentInroCircle2.setImageResource(R.drawable.sh_cir_red);
                fragmentInroCircle3.setImageResource(R.drawable.sh_cir_orange);
                break;
        }
    }

    @OnClick(R.id.fragment_inro_btn_next)
    void onViewClicked() {
        int current = getItem(+1);
        if (current < layout.length) {
            fragmentInroVpSlider.setCurrentItem(current);
        } else {
            lunchHomeScreen();
        }
    }

    @Override
    public void onBack() {
//        getActivity().finish();
    }
}
