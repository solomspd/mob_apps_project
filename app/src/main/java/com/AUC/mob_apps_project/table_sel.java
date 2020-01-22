package com.AUC.mob_apps_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

class pos {
    float x, y;
    int cont;
    String str_cont;
    int id;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_sel);

        data = new ArrayList<>();
        data.add(new pos(123, 200, 1, ""));
        data.add(new pos(200, 300, 1, ""));
        data.add(new pos(350, 300, 1, ""));

        layout = findViewById(R.id.table_edit_canvas);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (pos i : data) {
            ImageButton btn = new ImageButton(this);
            btn.setLayoutParams(params);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setImageDrawable(getResources().getDrawable(R.drawable.test, null));
            int new_id = View.generateViewId();
            btn.setId(new_id);
            i.id = new_id;
            btn.setX(i.x);
            btn.setY(i.y);
            layout.addView(btn);
        }

    }

    void init() {

    }


}