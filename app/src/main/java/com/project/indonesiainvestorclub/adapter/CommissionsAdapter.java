package com.project.indonesiainvestorclub.adapter;

import com.project.indonesiainvestorclub.BR;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Commissions;

import java.util.ArrayList;
import java.util.List;

public class CommissionsAdapter extends RVBaseAdapter {

    private List<Commissions> models;
    private ActionInterface.AdapterItemListener<Commissions> listener;

    public CommissionsAdapter() {
        this.models = new ArrayList<>();
    }

    public void setModels(List<Commissions> models) {
        this.models = models;
    }

    public void setListener(
            ActionInterface.AdapterItemListener<Commissions> listener) {
        this.listener = listener;
    }

    @Override public void onBindViewHolder(RVViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.setVariable(BR.listener, this.listener);
        holder.binding.executePendingBindings();
    }

    @Override public Object getDataAtPosition(int position) {
        Commissions commissions = models.get(position);
        commissions.setIndex(position);

        return commissions;
    }

    @Override public int getLayoutIdForType(int viewType) {
        return R.layout.sub_network_item;
    }

    @Override public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }
}
