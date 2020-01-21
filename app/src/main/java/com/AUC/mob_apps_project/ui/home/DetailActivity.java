package com.AUC.mob_apps_project.ui.home;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.AUC.mob_apps_project.R;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = (ImageView) findViewById(R.id.imageView2);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mImageView.setImageResource(mBundle.getInt("countryFlag"));
        }
    }
}