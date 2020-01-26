package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.AUC.mob_apps_project.ui.logout.FragmentLogout;

public class control_panel extends AppCompatActivity {

    String rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        rest = getIntent().getStringExtra("rest");
        rest = "Mori Sushi";


        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(control_panel.this, MenuActivity.class);
                intent.putExtra("Restaurant", rest);
                intent.putExtra("owner", true);
                startActivity(intent);
            }
        });

        findViewById(R.id.payments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(control_panel.this, payment_history.class);
                intent.putExtra("rest", rest);
                startActivity(intent);
            }
        });

        findViewById(R.id.waitng_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(control_panel.this, waiting_list.class);
                intent.putExtra("rest", rest);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(control_panel.this, layout_editor.class);
                intent.putExtra("rest", rest);
                startActivity(intent);
            }
        });

        findViewById(R.id.log_out_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(control_panel.this, FragmentLogout.class);
                startActivity(intent);
            }
        });

    }
}
