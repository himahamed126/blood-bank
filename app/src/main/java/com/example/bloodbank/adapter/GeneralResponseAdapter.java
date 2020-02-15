package com.example.bloodbank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.General.GeneralData;

import java.util.ArrayList;
import java.util.List;

public class GeneralResponseAdapter extends BaseAdapter {

    private Context context;
    private List<GeneralData> generalList;
    private LayoutInflater inflater;
    public int selectedId = 0;

    public GeneralResponseAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater.from(context));

    }

    public void setData(List<GeneralData> data, String hint) {
        this.generalList = new ArrayList<>();
        this.generalList.add(new GeneralData(0, hint));
        this.generalList.addAll(data);
    }

    @Override
    public int getCount() {
        return generalList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner, null);

        TextView textView = view.findViewById(R.id.sp_text);
        textView.setText(generalList.get(position).getName());
        selectedId = generalList.get(position).getId();
        return view;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_spinner, null);

        TextView text = view.findViewById(R.id.sp_text);
        text.setText(generalList.get(i).getName());
        text.setTextColor(Color.parseColor("#9A0B0B"));
        selectedId = generalList.get(i).getId();

        return view;
    }
}
