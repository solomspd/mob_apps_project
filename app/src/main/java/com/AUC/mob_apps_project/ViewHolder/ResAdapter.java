package com.AUC.mob_apps_project.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.Model.Restaurant;
import com.AUC.mob_apps_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class ResAdapter extends RecyclerView.Adapter<ResViewHolder> {

    Context context;
    ArrayList<Restaurant> restaurant;

    public ResAdapter(Context c , ArrayList<Restaurant> p){
        this.context = c;
        this.restaurant = p;

    }

    @NonNull
    @Override
    public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResViewHolder(LayoutInflater.from(context).inflate(R.layout.listview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResViewHolder holder, int position)
    {
        //    holder.restImgView
        holder.RestTxt.setText(restaurant.get(position).getName());
        restaurant.get(position).getLongitude();
        restaurant.get(position).getLatitude();
     //   Log.d("gggggggggggggggggg",restaurant.get(position).getCity());


        Picasso.get().load(restaurant.get(position).getImage()).into(holder.restImgView);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {

                //   i.putExtra("RestId",Rest_ID);
                //   i.putExtra("FoodId", adapter.getRef(position).getKey());
                try {Intent i = new Intent(context, Restaurant.class);
                    context.startActivity(i);

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }
}
