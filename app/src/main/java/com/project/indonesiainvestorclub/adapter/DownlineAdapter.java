package com.project.indonesiainvestorclub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.project.indonesiainvestorclub.BR;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.SubNetworkNewItemBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Commissions;
import com.project.indonesiainvestorclub.models.NetworkDownline;

import com.project.indonesiainvestorclub.viewModels.DownlineListItemViewModel;
import java.util.ArrayList;
import java.util.List;

public class DownlineAdapter extends RecyclerView.Adapter<DownlineAdapter.DownlineItemHolder> {

  private final List<DownlineItemHolder> holders;
  private Context context;
  private List<NetworkDownline> models;
  private ActionInterface.AdapterItemListener<NetworkDownline> listener;

  public DownlineAdapter() {
    models = new ArrayList<>();
    holders = new ArrayList<>();
  }

  public void setModels(List<NetworkDownline> models) {
    this.models = models;
  }

  public void setListener(ActionInterface.AdapterItemListener<NetworkDownline> listener) {
    this.listener = listener;
  }

  @Override public DownlineItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    this.context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    SubNetworkNewItemBinding binding =
        DataBindingUtil.inflate(layoutInflater, R.layout.sub_network_new_item, parent, false);
    DownlineItemHolder holder = new DownlineItemHolder(context, binding);
    synchronized (holders) {
      holders.add(holder);
    }
    return holder;
  }

  @Override public void onBindViewHolder(DownlineItemHolder holder, int position) {
    holder.bind(models.get(position));
  }

  @Override public int getItemCount() {
    if (models == null) return 0;
    return models.size();
  }

  public class DownlineItemHolder extends RecyclerView.ViewHolder {

    private Context context;
    private SubNetworkNewItemBinding binding;
    private DownlineListItemViewModel viewModel;

    DownlineItemHolder(Context context, SubNetworkNewItemBinding binding) {
      super(binding.getRoot());
      this.context = context;
      this.binding = binding;
    }

    public void bind(NetworkDownline networkDownline) {
      viewModel = new DownlineListItemViewModel(context, binding, networkDownline, listener);
      binding.setViewModel(viewModel);
      binding.executePendingBindings();
    }
  }
}
