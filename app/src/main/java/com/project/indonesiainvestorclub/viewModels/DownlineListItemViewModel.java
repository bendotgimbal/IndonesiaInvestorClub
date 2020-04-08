package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.databinding.ObservableField;
import com.project.indonesiainvestorclub.databinding.SubNetworkNewItemBinding;
import com.project.indonesiainvestorclub.models.NetworkDownline;

public class DownlineListItemViewModel extends BaseViewModelWithCallback {

  private SubNetworkNewItemBinding binding;
  public ObservableField<String> name;
  public ObservableField<String> data;

  public DownlineListItemViewModel(Context context, SubNetworkNewItemBinding binding, NetworkDownline downline) {
    super(context);
    this.binding = binding;

    name = new ObservableField<>(downline.getName());
    data = new ObservableField<>(downline.getNetworkData().get(0).getPhrase());
  }

  @Override public void hideLoading() {

  }
}
