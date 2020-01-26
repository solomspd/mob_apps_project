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
import com.AUC.mob_apps_project.Model.Category;
import com.AUC.mob_apps_project.Model.Food;
import com.AUC.mob_apps_project.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MenuActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager LayoutManager;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    String Rest_ID="";
    boolean owner;
    static String table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FloatingActionButton fab = findViewById(R.id.fab2);
        Rest_ID = getIntent().getStringExtra("Restaurant");
        owner = getIntent().getExtras().getBoolean("owner");
        table = getIntent().getExtras().getString("table");
        Toast.makeText(getApplicationContext(),"Table "+table+" has been booked",Toast.LENGTH_SHORT).show();
        if (!owner) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent cartIntent = new Intent (MenuActivity.this,CartActivity.class);
                    cartIntent.putExtra("Restaurant",Rest_ID);
                    cartIntent.putExtra("table", table);
                    startActivity(cartIntent);}
            });
        } else {
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add, null));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            fab.hide();
        }

        //Firebase
        database = FirebaseDatabase.getInstance();

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(LayoutManager);
        TextView menuheader;
        menuheader = (TextView) findViewById(R.id.menuheader);


        category = database.getReference("Restaurant").child(Rest_ID).child("Category");
        menuheader.setText(Rest_ID);
        loadMenu();



    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category){
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position){
                    viewHolder.txtMenuName.setText((model.name).toUpperCase());
                    Picasso.get().load(model.image).into(viewHolder.imageView);

                final Category clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent i = new Intent(MenuActivity.this, FoodActivity.class);
                            i.putExtra("RestId",Rest_ID);
                            i.putExtra("Name", adapter.getRef(position).getKey());
                            i.putExtra("Title", adapter.getItem(position).name);
                            i.putExtra("owner", owner);
                        try {
                                startActivity(i);
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                            }

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }
}
