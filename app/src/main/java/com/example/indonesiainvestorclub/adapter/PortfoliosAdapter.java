package com.example.indonesiainvestorclub.adapter;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Portfolios;

import java.util.ArrayList;
import java.util.List;

public class PortfoliosAdapter extends RVBaseAdapter {

    private List<Portfolios> models;
    private ActionInterface.AdapterItemListener<Portfolios> listener;

    public PortfoliosAdapter() {
        this.models = new ArrayList<>();
    }

    public void setModels(List<Portfolios> models) {
        this.models = models;
    }

    public void setListener(
            ActionInterface.AdapterItemListener<Portfolios> listener) {
        this.listener = listener;
    }

    @Override public void onBindViewHolder(RVViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.setVariable(com.example.indonesiainvestorclub.BR.listener, this.listener);
        holder.binding.executePendingBindings();
    }

    @Override public Object getDataAtPosition(int position) {
        Portfolios portfolios = models.get(position);
        portfolios.setIndex(position);

        return portfolios;
    }

    @Override public int getLayoutIdForType(int viewType) {
        return R.layout.sub_network_item;
    }

    @Override public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }
}