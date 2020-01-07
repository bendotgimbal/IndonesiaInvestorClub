package com.example.indonesiainvestorclub.adapter;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Transactions;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RVBaseAdapter {

    private List<Transactions> models;
    private ActionInterface.AdapterItemListener<Transactions> listener;

    public TransactionsAdapter() {
        this.models = new ArrayList<>();
    }

    public void setModels(List<Transactions> models) {
        this.models = models;
    }

    public void setListener(
            ActionInterface.AdapterItemListener<Transactions> listener) {
        this.listener = listener;
    }

    @Override public void onBindViewHolder(RVViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.setVariable(com.example.indonesiainvestorclub.BR.listener, this.listener);
        holder.binding.executePendingBindings();
    }

    @Override public Object getDataAtPosition(int position) {
        Transactions transactions = models.get(position);
        transactions.setIndex(position);

        return transactions;
    }

    @Override public int getLayoutIdForType(int viewType) {
        return R.layout.sub_transaction_item;
    }

    @Override public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }
}
