package com.project.indonesiainvestorclub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.models.Monthly;
import java.util.ArrayList;

public class PerformanceListviewAdapter extends ArrayAdapter<Monthly> {

  public PerformanceListviewAdapter(@NonNull Context context, ArrayList<Monthly> monthlies) {
    super(context, 0, monthlies);
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Monthly monthly = getItem(position);

    if (convertView == null){
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.sub_performance_listview , parent, false);
    }

    TextView title = convertView.findViewById(R.id.title);
    TextView subtitle = convertView.findViewById(R.id.subtitle);

    if (monthly != null){
      title.setText(monthly.getMonth());
      subtitle.setText(monthly.getPercentage());
    }

    return convertView;

  }
}
