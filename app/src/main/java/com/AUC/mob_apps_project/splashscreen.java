package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class splashscreen extends AppCompatActivity {
//    private static int SPLASH_TIME_OUT = 10000;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent homeIntent = new Intent(splashscreen.this, LoginActivity.class);
//                startActivity(homeIntent);
//                finish();
//            }
//        },SPLASH_TIME_OUT);

         fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            check();
        } else {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
    void check(){

        FirebaseDatabase.getInstance().getReference("/Users/"+fAuth.getCurrentUser().getUid()+"/auth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x = dataSnapshot.getValue(String.class);
                if(x != null) {
//                    Toast.makeText(LoginActivity.this, x.auth, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(splashscreen.this, control_panel.class);
                    intent.putExtra("rest", x);
                    startActivity(intent);
                } else {
//                    Toast.makeText(LoginActivity.this, x.auth, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
