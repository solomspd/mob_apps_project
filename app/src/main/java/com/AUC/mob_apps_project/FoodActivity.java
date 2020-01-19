package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.Model.Food;
import com.AUC.mob_apps_project.ViewHolder.FoodViewHolder;
import com.AUC.mob_apps_project.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference food;
    String Cat_ID="";

    TextView foodheader;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager LayoutManager;

    FirebaseRecyclerAdapter<Food, MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        FloatingActionButton fab = findViewById(R.id.fab2);
        TextView foodheader = findViewById(R.id.foodheader);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Firebase
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Restaurant").child("Restaurant").child("Food");

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(LayoutManager);

        if(getIntent() != null){
            Cat_ID = getIntent().getStringExtra("Name");
            foodheader.setText(getIntent().getStringExtra("Title"));
        }
        if(!Cat_ID.isEmpty() && Cat_ID != null) {
            loadFood(Cat_ID);
        }


    }

    private void loadFood(String Cat_ID) {
        FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.menu_item,FoodViewHolder.class,food.orderByChild("menuId").equalTo(Cat_ID)){
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position){
                try {
                    viewHolder.txtMenuName1.setText((model.name).toUpperCase());
                    viewHolder.txtPrice1.setText(model.price + " LE");
                    Picasso.get().load(model.image).into(viewHolder.imageView1);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
                final Food clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getApplicationContext(),clickitem.name,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }
}
