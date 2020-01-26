package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.AUC.mob_apps_project.Common.CurrentRestaurant;
import com.AUC.mob_apps_project.Model.RestaurantClass;
import com.AUC.mob_apps_project.Model.UsersClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingRestaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_restaurant);
        String Rest_Name = getIntent().getStringExtra("Restaurant");
        check(Rest_Name);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadingRestaurant.this, RestaurantActivity.class);
                homeIntent.putExtra("Restaurant", getIntent().getStringExtra("Restaurant"));
                homeIntent.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                homeIntent.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                startActivity(homeIntent);
                finish();
            }
        },1000);
    }



    void check(String x){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingtbl = database.getReference("Restaurant/"+x+"/Information");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    CurrentRestaurant.restaurant = dataSnapshot.getValue(RestaurantClass.class);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        ratingtbl.addValueEventListener(postListener);

    }

}
