package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.Model.Food;
import com.AUC.mob_apps_project.Model.Rating;
import com.AUC.mob_apps_project.ViewHolder.CommentViewHolder;
import com.AUC.mob_apps_project.ViewHolder.FoodViewHolder;
import com.AUC.mob_apps_project.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CommentActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference food;
    String Rest_ID="";

    TextView foodheader;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager LayoutManager;

    FirebaseRecyclerAdapter<Rating, CommentViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Rest_ID = getIntent().getStringExtra("Restaurant");
        TextView foodheader = findViewById(R.id.foodheader);

        //Firebase
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Rating");

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recyclerComment);
        recycler_menu.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(LayoutManager);

            loadComment();



    }

    private void loadComment() {
        adapter = new FirebaseRecyclerAdapter<Rating, CommentViewHolder>(Rating.class,R.layout.comment_layout,CommentViewHolder.class,food){
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Rating model, int position){
                try {
                    viewHolder.txtUserName.setText((model.getFullname()));
                    viewHolder.txtComment.setText(model.getComment());
                    viewHolder.ratingBar.setRating(Float.parseFloat(model.getValue()));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
                final Rating clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
//                        Intent i = new Intent(CommentActivity.this, FoodDetailActivity.class);
//                        i.putExtra("RestId",Rest_ID);
//                        i.putExtra("FoodId", adapter.getRef(position).getKey());
//                        try {
//                            startActivity(i);
//                        }catch(Exception e){
//                            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
//                        }
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }
}
