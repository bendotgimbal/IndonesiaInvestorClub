package com.example.indonesiainvestorclub.adapter;

import com.example.indonesiainvestorclub.BR;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Tweets;
import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RVBaseAdapter {

  private List<Tweets> models;
  private ActionInterface.AdapterItemListener<Tweets> listener;

  public TweetsAdapter() {
    this.models = new ArrayList<>();
  }

  public void setModels(List<Tweets> models) {
    this.models = models;
  }

  public void setListener(
      ActionInterface.AdapterItemListener<Tweets> listener) {
    this.listener = listener;
  }

  @Override public void onBindViewHolder(RVViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.binding.setVariable(BR.listener, this.listener);
    holder.binding.executePendingBindings();
  }

  @Override public Object getDataAtPosition(int position) {
    Tweets tweets = models.get(position);
    tweets.setIndex(position);

    return tweets;
  }

  @Override public int getLayoutIdForType(int viewType) {
    return R.layout.sub_tweet_item;
  }

  @Override public int getItemCount() {
    if (models == null) return 0;
    return models.size();
  }
}
