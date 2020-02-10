package com.project.indonesiainvestorclub.adapter;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.models.Month;
import java.util.ArrayList;
import java.util.List;

public class PerformanceYearAdapter extends RVBaseAdapter {

  private List<Month> models;

  public PerformanceYearAdapter() {
    this.models = new ArrayList<>();
  }

  public void setModels(List<Month> models) {
    this.models = models;
  }

  @Override public void onBindViewHolder(RVViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.binding.executePendingBindings();
  }

  @Override public Object getDataAtPosition(int position) {
    Month month = models.get(position);
    month.setIndex(position);

    return month;
  }

  @Override public int getLayoutIdForType(int viewType) {
    return R.layout.sub_fixed_column;
  }

  @Override public int getItemCount() {
    if (models == null) return 0;
    return models.size();
  }
}
