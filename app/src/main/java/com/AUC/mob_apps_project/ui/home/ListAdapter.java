package com.AUC.mob_apps_project.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AUC.mob_apps_project.R;

public class ListAdapter extends ArrayAdapter<String> {

    String[] strings;
    int[] imgs;
    Context mContext;

    public ListAdapter(Context context, String[] RestaurauntName, int[] Logo) {
        super(context, R.layout.listview_item);
        this.strings = RestaurauntName;
        this.imgs = Logo;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return strings.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.mImg = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mStrng = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mImg.setImageResource(imgs[position]);
        mViewHolder.mStrng.setText(strings[position]);

        return convertView;
    }

    static class ViewHolder {
        ImageView mImg;
        TextView mStrng;
    }

}
