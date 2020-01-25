package com.AUC.mob_apps_project.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.AUC.mob_apps_project.Interface.ItemClickListener;
import com.AUC.mob_apps_project.R;

public class ResViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    ImageView restImgView;
    TextView RestTxt;
    private ItemClickListener itemClickListener;
    public ResViewHolder(View itemView)
    {
        super(itemView);
        restImgView = (ImageView)  itemView.findViewById(R.id.imageView);
        RestTxt = (TextView) itemView.findViewById((R.id.textView));
        itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.OnClick(view,getAdapterPosition(),false);

    }
//    public void onClick(final int position)
//    {
//        public void onClick(View v)
//        {
//            Toast.makeText(context, position+"isClicked",Toast.LENGTH_SHORT ).show();
//
//        }
//    }
}
