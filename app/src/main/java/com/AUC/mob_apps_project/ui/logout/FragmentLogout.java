package com.AUC.mob_apps_project.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.AUC.mob_apps_project.LoginActivity;
import com.AUC.mob_apps_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLogout extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getActivity(),LoginActivity.class));
        Toast.makeText(getActivity(),"Logged out Successfully! ", Toast.LENGTH_LONG).show();

        return view;
    }

}