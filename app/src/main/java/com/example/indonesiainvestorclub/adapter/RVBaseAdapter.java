package com.example.indonesiainvestorclub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.indonesiainvestorclub.BR;

public abstract class RVBaseAdapter extends RecyclerView.Adapter<RVBaseAdapter.RVViewHolder> {

  protected Context context;

  @Override
  public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    this.context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ViewDataBinding binding =
        DataBindingUtil.inflate(layoutInflater, getLayoutIdForType(viewType), parent, false);
    return new RVViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(RVViewHolder holder, int position) {
    holder.bind(getDataAtPosition(position));
  }

  public abstract Object getDataAtPosition(int position);

  public abstract int getLayoutIdForType(int viewType);

  public class RVViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding binding;

    public RVViewHolder(ViewDataBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Object obj) {
      binding.setVariable(BR.obj, obj);
      binding.executePendingBindings();
    }
  }
}