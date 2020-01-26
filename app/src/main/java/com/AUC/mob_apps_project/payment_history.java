package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class payment_history extends AppCompatActivity implements add_payment.add_pay_inter{

    RecyclerView list;
    ArrayList<Request> list_data;
    pay_adapter adapter;
    String rest;
    ArrayList<String> id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        rest = getIntent().getExtras().getString("rest");

        id = new ArrayList<>();

        list_data = get_list();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        list = findViewById(R.id.payment_history_list);

        DividerItemDecoration decorate = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
        list.addItemDecoration(decorate);

        list.setLayoutManager(layoutManager);

        adapter = new pay_adapter(list_data);

        list.setAdapter(adapter);

        findViewById(R.id.add_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment input = new add_payment();
                input.show(getSupportFragmentManager(), "Add a new item");
            }
        });

    }

    private ArrayList<Request> get_list() {
        final ArrayList<Request> ret = new ArrayList<>();
        id.clear();
        FirebaseDatabase.getInstance().getReference("/Requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    Request temp = new Request();
                    if (rest.equals(next.child("restaurant").getValue())) {
                        temp.setFullname((String) next.child("fullname").getValue());
                        temp.setTotal((String) next.child("total").getValue());
                        temp.setTable((String) next.child("table").getValue());
                        temp.setTime((long) next.child("time").getValue());
                        id.add(next.getKey());
                        ret.add(temp);
                    }
                }
                list_data = ret;
                try {
                    adapter.notifyDataSetChanged();
                } catch (Exception err) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(payment_history.this, "Check your connection and try again", Toast.LENGTH_LONG).show();
            }
        });
        return ret;
    }

    @Override
    public void onAdd(DialogFragment dialog) {
        Request ret = new Request();
        ret.setFullname((((TextView)dialog.getDialog().findViewById(R.id.add_pay_name)).getText().toString()));
        ret.setTotal((((TextView)dialog.getDialog().findViewById(R.id.add_pay_amm)).getText().toString()));
        ret.setTable((((TextView)dialog.getDialog().findViewById(R.id.add_pay_table)).getText().toString()));
        ret.setRestaurant(rest);
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        try {
            Date time = format.parse(((EditText) dialog.getDialog().findViewById(R.id.add_pay_date)).getText().toString() + " " + ((EditText) dialog.getDialog().findViewById(R.id.add_pay_time)).getText().toString());
            ret.setTime(time.getSeconds());
        } catch (Exception err) {
            System.out.println("error in parsing date");
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Requests");
        String new_key = ref.push().getKey();
        ref.child(new_key).setValue(ret);
        id.add(new_key);
        list_data.add(ret);
        adapter.notifyItemChanged(list_data.size()-1);
//        ref.push().setValue(ret);
    }
}

class pay_adapter extends RecyclerView.Adapter<pay_adapter.viewholder> {

    private ArrayList<Request> data;

    pay_adapter(ArrayList<Request> in) {
        data = in;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_item, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        holder.name.setText(data.get(position).getFullname());
        holder.table.setText(data.get(position).getTable());
        holder.amm.setText(String.valueOf(data.get(position).getTotal()));
        Date time = new Date(data.get(position).getTime());
        holder.time.setText(time.getHours() + ":" + time.getMinutes());
        holder.date.setText(time.getDay()+"/"+time.getMonth());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        TextView time, amm, name, table, date;

        viewholder(View view) {
            super(view);
            time = view.findViewById(R.id.pay_time);
            amm = view.findViewById(R.id.pay_amount);
            name = view.findViewById(R.id.pay_name);
            table = view.findViewById(R.id.pay_table);
            date = view.findViewById(R.id.pay_date);
        }
    }

}