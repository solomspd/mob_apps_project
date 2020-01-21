package com.AUC.mob_apps_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

class pos {
    float x, y;
    int cont;
    String str_cont;

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

        layout = findViewById(R.id.table_sel_canvas);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (pos i : data) {
            ImageButton btn = new ImageButton(this);
            btn.setLayoutParams(params);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setImageDrawable(getResources().getDrawable(R.drawable.test, null));
            btn.setX(i.x);
            btn.setY(i.y);
            layout.addView(btn);
        }

    }

    void init() {

    }


}