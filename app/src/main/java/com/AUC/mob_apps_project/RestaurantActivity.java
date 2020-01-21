package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.AUC.mob_apps_project.Database.Database;
import com.AUC.mob_apps_project.Common.CurrentUser;
import com.AUC.mob_apps_project.ui.Navigation.Navigationn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantActivity extends AppCompatActivity {
    String Rest_Name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Rest_Name = getIntent().getStringExtra("Restaurant");
        Button button = findViewById(R.id.menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new Database(getBaseContext()).cleanCart(); // Empty Cart
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            i.putExtra("Restaurant", Rest_Name);
            startActivity(i);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Directios Map To Restaurant",Toast.LENGTH_SHORT).show();
                Navigate();
            }
        });
    }


    public void Navigate()
    {
        Intent intent = new Intent(this, Navigationn.class);
        startActivity(intent);

    }



}
