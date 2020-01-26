package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import static androidx.core.content.ContextCompat.startActivity;

public class waiting_list extends AppCompatActivity implements add_wait_list.add_wait_inter{

    wait_adapter adapter;
    ArrayList<Request> list_data;
    RecyclerView list;
    String rest;
    ArrayList<String> id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

        id = new ArrayList<>();

        rest = getIntent().getExtras().getString("rest");

        list_data = get_list();

        list = findViewById(R.id.wait_list_list);

        adapter = new wait_adapter(list_data, id);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        findViewById(R.id.add_wait_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment input = new add_wait_list();
                input.show(getSupportFragmentManager(), "Add a new reservation");
            }
        });

        list.setLayoutManager(layoutManager);

        list.setAdapter(adapter);

    }

    ArrayList<Request> get_list(){
        final ArrayList<Request> ret = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("/Requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    Request temp = new Request();
                    if (rest.equals(next.child("restaurant").getValue()) && next.child("status").getValue().equals("waiting")) {
                        temp.setFullname((String) next.child("fullname").getValue());
                        temp.setTable((String) next.child("table").getValue());
                        temp.setTime((long) next.child("time").getValue());
                        temp.setPhone((String) next.child("phone").getValue());
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
                Toast.makeText(waiting_list.this, "Check your connection and try again", Toast.LENGTH_LONG).show();
            }
        });
        return ret;

    }


    @Override
    public void onAdd(DialogFragment dialog) {
        Request ret = new Request();
        ret.setFullname(((EditText)dialog.getDialog().findViewById(R.id.add_wait_name)).getText().toString());
        ret.setPhone(((EditText)dialog.getDialog().findViewById(R.id.add_wait_phone)).getText().toString());
        ret.setTable(((EditText)dialog.getDialog().findViewById(R.id.add_wait_table)).getText().toString());
        ret.setStatus("waiting");
        ret.setRestaurant(rest);
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        try {
            Date time = format.parse(((EditText) dialog.getDialog().findViewById(R.id.add_wait_date)).getText().toString() + " " + ((EditText) dialog.getDialog().findViewById(R.id.add_wait_time)).getText().toString());
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

class wait_adapter extends RecyclerView.Adapter<wait_adapter.viewholder> {

    ArrayList<Request> data;
    ArrayList<String> ids;

    wait_adapter(ArrayList<Request> in, ArrayList<String> id) {
        data = in;
        ids = id;
    }

    wait_adapter(ArrayList<Request> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public wait_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_list_item, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull wait_adapter.viewholder holder, final int position) {
        holder.name.setText(data.get(position).getFullname());
        holder.time.setText(String.valueOf(data.get(position).getTime()));
        holder.table.setText(data.get(position).getTable());
        Date time = new Date(data.get(position).getTime());
        holder.time.setText(time.getHours() + ":" + time.getMinutes());
        holder.date.setText(time.getDay()+"/"+time.getMonth());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("/Requests/"+ids.get(position)).removeValue();
                ids.remove(position);
                data.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
//                intent.setType("vnd.android-dir/mms-sms");
//                intent.putExtra("address", data.get(position).getPhone());
//                startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView time, name, table, date;
        ImageView cancel, contact;

        viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.wait_time);
            name = itemView.findViewById(R.id.wait_name);
            table = itemView.findViewById(R.id.wait_table);
            date = itemView.findViewById(R.id.wait_date);
            cancel = itemView.findViewById(R.id.wait_cancel);
            contact = itemView.findViewById(R.id.wait_call);
        }
    }

}
