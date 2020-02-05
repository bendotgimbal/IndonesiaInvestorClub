package com.project.indonesiainvestorclub.adapter;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.models.CurrentData;
import java.util.ArrayList;
import java.util.List;

public class ParticipantAdapter extends RVBaseAdapter {

  private List<CurrentData> models;

  public ParticipantAdapter() {
    this.models = new ArrayList<>();
  }

  public void setModels(List<CurrentData> models) {
    this.models = models;
  }

  @Override public void onBindViewHolder(RVViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.binding.executePendingBindings();
  }

  @Override public Object getDataAtPosition(int position) {
    CurrentData currentData = models.get(position);
    currentData.setIndex(position);

    return currentData;
  }

  @Override public int getLayoutIdForType(int viewType) {
    return R.layout.sub_participant_item;
  }

  @Override public int getItemCount() {
    if (models == null) return 0;
    return models.size();
  }
}
