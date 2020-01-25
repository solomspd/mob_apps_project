package com.AUC.mob_apps_project.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Model.Restaurant;
import com.AUC.mob_apps_project.R;
import com.AUC.mob_apps_project.ViewHolder.ResAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Restaurant> list;
    ResAdapter adapter;
    SearchView searchInp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHome);
        searchInp = (SearchView) findViewById(R.id.searchInp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Restaurant");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list= new ArrayList<Restaurant>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Restaurant p =dataSnapshot1.getValue(Restaurant.class);
                    Log.d("hhhhhhhhhhh",p.name);
                    list.add(p);
                }

                adapter = new ResAdapter(HomeFragment.this,list);
                recyclerView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeFragment.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
