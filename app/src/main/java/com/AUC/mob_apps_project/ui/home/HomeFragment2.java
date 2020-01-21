package com.AUC.mob_apps_project.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.AUC.mob_apps_project.R;
import com.AUC.mob_apps_project.RestaurantActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment2 extends Fragment {

    FirebaseDatabase database;
    DatabaseReference food;
    String Cat_ID="",Rest_ID="";
    //    private HomeViewModel homeViewModel;
    ListView act1list;
    String[] ResName = new String[]{"House of Cocoa", "Mori Sushi", "McDonalds"};
    int[] imgs = {R.drawable.houseofcocoalogoo ,R.drawable.morisushilogoo,R.drawable.mcdonaldslogoo} ;
    ImageButton imgbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.search_fragment);

        imgbtn = (ImageButton) getView().findViewById(R.id.imgbtn);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMap();
            }
        });


        //assign list
        act1list = (ListView) getView().findViewById(R.id.listview);
        // categories= {"ss"};
//        int[] nums2 = new int[categories.length];
//        for(int i=0;i < categories.length ; i++)
//        {
//            nums2[i]= R.drawable.noimg;
//
//        }
        //   imgs=nums2;
        ListAdapter myAdapter = new ListAdapter(HomeFragment2.this.getContext(), ResName, imgs);
        act1list.setAdapter(myAdapter);
        act1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra("Restaurant", "Restaurant");
                startActivity(intent);
//                Intent mIntent = new Intent(HomeFragment.this.getContext(), DetailActivity.class);
//                mIntent.putExtra("RestaurauntName", ResName[i]);
//                mIntent.putExtra("Logo", imgs[i]);
//                startActivity(mIntent);
            }
        });


    }


    public void openMap() {
        Intent intent = new Intent(this.getContext(), map.class);
        startActivity(intent);

    }
}
