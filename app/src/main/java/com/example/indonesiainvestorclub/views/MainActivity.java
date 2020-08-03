package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.adapters.MyAdapter;
import com.example.indonesiainvestorclub.models.Parent;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView recyclerView = findViewById(R.id.rvM);
    ArrayList<Parent> list = new ArrayList<>(10);

    for (int i = 0; i < 10; i++)
      list.add(new Parent("Parent " + i));

    final MyAdapter myAdapter = new MyAdapter(list);

    myAdapter.setExpanded(false);

    recyclerView.setAdapter(myAdapter);
  }
}
