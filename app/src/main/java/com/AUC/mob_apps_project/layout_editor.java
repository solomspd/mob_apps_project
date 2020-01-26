package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

enum sel {round_table, none, other}

class rest_element {
    public int id;
    public float x, y;
    public sel selection;

    rest_element (int id, sel selection, float x, float y) {
        this.id = id;
        this.selection = selection;
        this.x = x;
        this.y = y;
    }



}

public class layout_editor extends AppCompatActivity {


    sel cur_sel;
    ArrayList<rest_element> tables;
    ConstraintLayout root;
    String rest;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_editor);

        cur_sel = sel.none;
        tables = new ArrayList<>();
        root = findViewById(R.id.table_edit_canvas);
        rest = getIntent().getExtras().getString("rest");

        findViewById(R.id.round_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur_sel = sel.round_table;
            }
        });

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur_sel = sel.other;
            }
        });

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ImageView temp = new ImageView(layout_editor.this);
                    temp.setImageDrawable(getResources().getDrawable(R.drawable.test, null));
                    int new_id = View.generateViewId();
                    temp.setId(new_id);
                    float x = event.getX()-55;
                    temp.setX(x);
                    float y = event.getY()-55;
                    temp.setY(y);
                    tables.add(new rest_element(new_id, cur_sel, x, y));
                    temp.setClickable(true);
                    temp.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch(event.getAction() & MotionEvent.ACTION_MASK) {
                                case MotionEvent.ACTION_DOWN:
                                case MotionEvent.ACTION_MOVE: {
                                    v.setX(event.getRawX()-v.getHeight()/2);
                                    v.setY(event.getRawY()-v.getWidth());
                                    break;
                                }
                            }
                            return true;
                        }
                    });
                    root.addView(temp);
                }
                return true;
            }
        });

        findViewById(R.id.save_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (rest_element i : tables) {
                    i.x = findViewById(i.id).getX();
                    i.y = findViewById(i.id).getY();
                }
                FirebaseDatabase.getInstance().getReference("/Restaurant/"+rest+"/layout").setValue(tables);
                startActivity(new Intent(layout_editor.this, control_panel.class));
            }
        });


    }
}
