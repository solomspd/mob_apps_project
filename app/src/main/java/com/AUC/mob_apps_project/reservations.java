package com.AUC.mob_apps_project;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AUC.mob_apps_project.Model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class reservations extends Fragment {

    private ReservationsViewModel mViewModel;

    RecyclerView list;
    ArrayList<Request> data_list;
    wait_adapter adapter;

    public static reservations newInstance() {
        return new reservations();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reservations_fragment, container, false);

        data_list = get_list();

        list = view.findViewById(R.id.reserve_list);

        adapter = new wait_adapter(data_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        list.setLayoutManager(layoutManager);

        list.setAdapter(adapter);

        return view;
    }

    private ArrayList<Request> get_list() {
        final ArrayList<Request> ret = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    Request temp = new Request();
                    if (next.child("fullname").equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                        temp.setFullname((String) next.child("fullname").getValue());
                        temp.setTable((String) next.child("table").getValue());
                        temp.setTime((long) next.child("time").getValue());
                        temp.setPhone((String) next.child("phone").getValue());
                        ret.add(temp);
                    }
                }
                data_list = ret;
                try {
                    adapter.notifyDataSetChanged();
                } catch (Exception err) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return ret;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReservationsViewModel.class);
        // TODO: Use the ViewModel
    }

}

