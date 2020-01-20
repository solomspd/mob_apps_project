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
import android.widget.ImageView;

public class table_sel extends AppCompatActivity {

    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConstraintLayout constraintLayout;
        super.onCreate(savedInstanceState);

        // Create a ConstraintLayout in which to add the ImageView
//        constraintLayout = new ConstraintLayout(this);

        custom_canvas canvas = new custom_canvas(this);

        canvas.setBackgroundColor(Color.BLUE);

        setContentView(canvas);

        custom_butt butt = new custom_butt(this);

        canvas.draw(butt);

    }

    void init() {

    }


}

class custom_butt extends View {

    public custom_butt(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

}

class custom_canvas extends View {

    public custom_canvas(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    public void draw(custom_butt butt) {

    }
}

