package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

enum sel {round_table, none, other}

class rest_element {
    int id;
    float x, y;
    sel selection;

    rest_element (int id, sel selection) {
        this.id = id;
        this.selection = selection;
    }

}

public class layout_editor extends AppCompatActivity {


    sel cur_sel;
    ArrayList<rest_element> tables;
    ConstraintLayout root;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_editor);

        cur_sel = sel.none;
        tables = new ArrayList<>();
        root = findViewById(R.id.table_edit_canvas);

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
                    tables.add(new rest_element(new_id, cur_sel));
                    temp.setX(event.getX()-55);
                    temp.setY(event.getY()-55);
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
                
            }
        });


    }
}
