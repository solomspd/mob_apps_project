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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.AUC.mob_apps_project.Common.CurrentRestaurant;
import com.AUC.mob_apps_project.FoodActivity;
import com.AUC.mob_apps_project.LoadingRestaurant;
import com.AUC.mob_apps_project.MenuActivity;
import com.AUC.mob_apps_project.Model.RestaurantClass;
import com.AUC.mob_apps_project.Model.UsersClass;
import com.AUC.mob_apps_project.R;
import com.AUC.mob_apps_project.RestaurantActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    //    private HomeViewModel homeViewModel;
    ListView act1list;
    String[] ResName = new String[]{"House of Cocoa", "Mori Sushi", "McDonalds"};
    int[] imgs = {R.drawable.houseofcocoalogoo ,R.drawable.morisushilogoo,R.drawable.mcdonaldslogoo} ;
    ImageButton imgbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        view.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), RestaurantActivity.class);
//                i.putExtra("Restaurant", "Restaurant");
//                startActivity(i);
//
//            }
//        });

        return view;
    }




    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.search_fragment);

        imgbtn = (ImageButton) getView().findViewById(R.id.imgbtn);

        Log.d("hhhhhhhhhhhh", "hhhhhhhhhhhhh");
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
        ListAdapter myAdapter = new ListAdapter(HomeFragment.this.getContext(), ResName, imgs);
        act1list.setAdapter(myAdapter);
        act1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), LoadingRestaurant.class);
                intent.putExtra("Restaurant", "Mori Sushi");
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
