package com.AUC.mob_apps_project.ui.home;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class HomeFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    static ArrayList<Restaurant> Restaurantslist=new ArrayList<Restaurant>();;
    ResAdapter adapter;
    SearchView searchInp;
    ImageButton imgbtn;

    //    HomeFragment(){
//
//    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.fragment_home);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewHome);
        searchInp = (SearchView) getView().findViewById(R.id.searchInp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        imgbtn = (ImageButton) getView().findViewById(R.id.imgbtn);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Restaurant");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Restaurantslist= new ArrayList<Restaurant>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Restaurant p =dataSnapshot1.child("Information").getValue(Restaurant.class);
                  //  Log.d("hhhhhhhhhhh", String.valueOf(p.longitude));
                    Restaurantslist.add(p);
                }
                setRestaurantslist(Restaurantslist);

                adapter = new ResAdapter(HomeFragment.this.getContext(),Restaurantslist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeFragment.this.getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private static void setRestaurantslist(ArrayList<Restaurant> r){
        Restaurantslist = r;
        Log.d("reststuff2", String.valueOf(r.get(0).getLongitude()));

    }

    public static ArrayList<Restaurant> getRestaurantslist(){

        Log.d("reststuff", String.valueOf(Restaurantslist.get(0).getLongitude()));
        return Restaurantslist;
    }


    public void openMap() {
        Intent intent = new Intent(this.getContext(), map.class);
        startActivity(intent);

    }


}
