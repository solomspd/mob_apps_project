package com.AUC.mob_apps_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

class pos {
    double x;
    double y;
    long cont;
    String str_cont;
    long id;

    pos () {

    }

    pos (float x, float y, int cont, String str_cont) {
        this.x = x;
        this.y = y;
        this.cont = cont;
        this.str_cont = str_cont;
    }
}

public class table_sel extends AppCompatActivity {

    ConstraintLayout layout;
    ArrayList<pos> data;
    DatabaseReference ref;
    String rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_sel);
        rest = getIntent().getExtras().getString("Restaurant");

        ref = FirebaseDatabase.getInstance().getReference("Restaurant").child(rest).child("layout");

        data = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    pos temp = new pos();
                    temp.x = (double) next.child("x").getValue();
                    temp.y = (double) next.child("y").getValue();
                    temp.id = (long) next.child("id").getValue();
                    data.add(temp);
                }

                layout = findViewById(R.id.table_edit_canvas);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                for (final pos i : data) {
                    ImageButton btn = new ImageButton(table_sel.this);
                    btn.setLayoutParams(params);
                    btn.setBackgroundColor(Color.TRANSPARENT);
                    btn.setImageDrawable(getResources().getDrawable(R.drawable.test, null));
                    int new_id = View.generateViewId();
                    btn.setId(new_id);
                    i.id = new_id;
                    btn.setX((float) i.x);
                    btn.setY((float) i.y);
                    layout.addView(btn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("table", i.id);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    void init() {

    }


}