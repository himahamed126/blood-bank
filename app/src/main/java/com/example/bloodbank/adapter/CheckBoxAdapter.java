package com.example.bloodbank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.General.GeneralData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {

    private Context context;
    private List<GeneralData> generalDataList;
    private List<String> oldList;
    public List<Integer> newIds = new ArrayList<>();

    private static final String TAG = "CheckBoxAdapter";

    public CheckBoxAdapter(Context context, List<GeneralData> generalDataList, List<String> oldList) {
        this.context = context;
        this.generalDataList = generalDataList;
        this.oldList = oldList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        GeneralData data = generalDataList.get(position);
        holder.itemCheckBoxCh.setText(data.getName());
        try {
            if (oldList.contains(String.valueOf(data.getId()))) {
                holder.itemCheckBoxCh.setChecked(true);
                newIds.add(data.getId());
            } else {
                holder.itemCheckBoxCh.setChecked(false);
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }

    }

    private void setAction(ViewHolder holder, int position) {
        GeneralData data = generalDataList.get(position);

        holder.itemCheckBoxCh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < newIds.size(); i++) {
                        if (data.getId().equals(newIds.get(i))) {
                            newIds.remove(i);
                            break;
                        }
                    }
                } else {
                    newIds.add(data.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return generalDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_check_box_ch)
        CheckBox itemCheckBoxCh;
        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
