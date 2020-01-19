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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Firebase
        database = FirebaseDatabase.getInstance();

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(LayoutManager);
        TextView menuheader;
        menuheader = (TextView) findViewById(R.id.menuheader);


        Rest_ID = getIntent().getStringExtra("Restaurant");
        category = database.getReference("Restaurant").child(Rest_ID).child("Category");
        menuheader.setText(Rest_ID);
        loadMenu();



    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category.orderByChild("name")){
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position){
                    viewHolder.txtMenuName.setText((model.name).toUpperCase());
                    Picasso.get().load(model.image).into(viewHolder.imageView);

                final Category clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getApplicationContext(),clickitem.name,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MenuActivity.this, FoodActivity.class);
                            i.putExtra("Name", adapter.getRef(position).getKey());
                            i.putExtra("Title", adapter.getItem(position).name);
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
