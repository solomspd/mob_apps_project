package com.AUC.mob_apps_project.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName1;
    public ImageView imageView1;
    public TextView txtPrice1;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName1 = (TextView) itemView.findViewById(R.id.menu_name);
        imageView1 = (ImageView) itemView.findViewById(R.id.menu_image);
        txtPrice1 = (TextView) itemView.findViewById(R.id.menu_price);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.OnClick(view,getAdapterPosition(),false);

    }


}
