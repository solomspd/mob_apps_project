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

public class payment_history extends AppCompatActivity {

    RecyclerView list;
    ArrayList<pay_item> list_data;
    pay_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        list_data = new ArrayList<>();

        list_data.add(new pay_item(10, 100, "jow", "10"));
        list_data.add(new pay_item(10, 100, "joe", "10"));
        list_data.add(new pay_item(10, 100, "joe", "10"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        list = findViewById(R.id.payment_history_list);

        DividerItemDecoration decorate = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
        list.addItemDecoration(decorate);

        list.setLayoutManager(layoutManager);

        adapter = new pay_adapter(list_data);

        list.setAdapter(adapter);

    }
}

class pay_item {
    int time;
    double ammount;
    String name, table;

    pay_item(int time, double ammount, String name, String table) {
        this.time = time;
        this.ammount = ammount;
        this.name = name;
        this.table = table;
    }
}

class pay_adapter extends RecyclerView.Adapter<pay_adapter.viewholder> {

    private ArrayList<pay_item> data;

    pay_adapter(ArrayList<pay_item> in) {
        data = in;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_item, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.table.setText(data.get(position).table);
        holder.amm.setText(String.valueOf(data.get(position).ammount));
        holder.time.setText(String.valueOf(data.get(position).time));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        TextView time, amm, name, table;

        viewholder(View view) {
            super(view);
            time = view.findViewById(R.id.pay_time);
            amm = view.findViewById(R.id.pay_amount);
            name = view.findViewById(R.id.pay_name);
            table = view.findViewById(R.id.pay_table);
        }
    }

}