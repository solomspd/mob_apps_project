package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class waiting_list extends AppCompatActivity {

    wait_adapter adapter;
    ArrayList<wait_item> list_data;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

        list_data = new ArrayList<>();

        list_data.add(new wait_item(10, 100, "joe", "10H"));
        list_data.add(new wait_item(10, 100, "joe", "10H"));
        list_data.add(new wait_item(10, 100, "joe", "10H"));
        list_data.add(new wait_item(10, 100, "joe", "10H"));

        list = findViewById(R.id.wait_list_list);

        adapter = new wait_adapter(list_data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        list.setLayoutManager(layoutManager);

        list.setAdapter(adapter);

    }

}

class wait_item {
    int time, number;
    String name, table;

    wait_item(int time, int number, String name, String table) {
        this.time = time;
        this.name = name;
        this.table = table;
        this.number = number;
    }
}

class wait_adapter extends RecyclerView.Adapter<wait_adapter.viewholder> {

    ArrayList<wait_item> data;

    wait_adapter(ArrayList<wait_item> in) {
        data = in;
    }

    @NonNull
    @Override
    public wait_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_list_item, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull wait_adapter.viewholder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.time.setText(String.valueOf(data.get(position).time));
        holder.table.setText(data.get(position).table);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView time, name, table;

        viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.wait_time);
            name = itemView.findViewById(R.id.wait_name);
            table = itemView.findViewById(R.id.wait_table);
        }
    }

}
