package com.AUC.mob_apps_project.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.R;

public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtUserName, txtComment;
    public RatingBar ratingBar;

    private ItemClickListener itemClickListener;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);


        txtComment = (TextView) itemView.findViewById(R.id.txtComment);
        txtUserName = (TextView)  itemView.findViewById(R.id.txtUserName);
        ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

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