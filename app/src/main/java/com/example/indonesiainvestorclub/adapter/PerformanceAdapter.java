package com.example.indonesiainvestorclub.adapter;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Month;

import java.util.ArrayList;
import java.util.List;

public class PerformanceAdapter extends RVBaseAdapter {

    private List<Month> models;
    private ActionInterface.AdapterItemListener<Month> listener;

    public PerformanceAdapter() {
        this.models = new ArrayList<>();
    }

    public void setModels(List<Month> models) {
        this.models = models;
    }

    public void setListener(
            ActionInterface.AdapterItemListener<Month> listener) {
        this.listener = listener;
    }

    @Override public void onBindViewHolder(RVViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.setVariable(com.example.indonesiainvestorclub.BR.listener, this.listener);
        holder.binding.executePendingBindings();
    }

    @Override public Object getDataAtPosition(int position) {
        Month month = models.get(position);
        month.setIndex(position);

        return month;
    }

    @Override public int getLayoutIdForType(int viewType) {
        return R.layout.sub_performance_item;
    }

    @Override public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }
}