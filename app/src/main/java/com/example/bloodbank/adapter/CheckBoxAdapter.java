package com.example.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.General.GeneralData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {

    private Context context;
    private List<GeneralData> generalDataList;

    private List<Integer> oldList;
    private List<Integer> newList;

    public CheckBoxAdapter(Context context, List list) {
        this.context = context;
        this.generalDataList = list;
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
        if (oldList.contains(data.getId())) {
            holder.itemCheckBoxCh.setChecked(true);
        } else {
            holder.itemCheckBoxCh.setChecked(false);
        }
    }

    private void setAction(ViewHolder holder, int position) {
        GeneralData data = generalDataList.get(position);

        holder.itemCheckBoxCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemCheckBoxCh.isChecked()) {
                    for (int i = 0; i < newList.size(); i++) {
                        if (newList.get(i).equals(data.getId())) {
                            newList.remove(i);
                            break;
                        }
                    }
                } else {
                    newList.add(data.getId());
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
