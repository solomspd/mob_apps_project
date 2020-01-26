package com.AUC.mob_apps_project.ui.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.AUC.mob_apps_project.Model.UsersClass;
import com.AUC.mob_apps_project.R;

public class ToolsFragment extends Fragment {

    TextView usrname;
    TextView email;
    Button editun;
    Button editemail;
    Button editpw;
    AlertDialog dialog;
    EditText editText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools,container,false);

            return view;
        }



        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            usrname = (TextView)getView().findViewById(R.id.usrname);
            email = (TextView) getView().findViewById(R.id.email);
            editun = (Button) getView().findViewById(R.id.editun);
            editemail = (Button) getView().findViewById(R.id.editemail);
            editpw = (Button) getView().findViewById(R.id.editpw);
            editText = new EditText(this.getContext());
//            dialog.setTitle("Edit Username");
//            dialog.setView(usrname);
//
//            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    editText.setText(usrname.getText());
//                    dialog.show();
//                }
//            });


//            dialog.setButton(new AlertDialog.Builder(context)
//                    .setTitle("Delete entry")
//                    .setMessage("Are you sure you want to delete this entry?")
//
//                    // Specifying a listener allows you to take an action before dismissing the dialog.
//                    // The dialog is automatically dismissed when a dialog button is clicked.
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Continue with delete operation
//                        }
//                    })
//
//                    // A null listener allows the button to dismiss the dialog and take no further action.
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show());

            editun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment simpleDialogFragment = new SimpleDialogFragment();
                    simpleDialogFragment.setCancelable(false);
                    simpleDialogFragment.show(getFragmentManager(), "Simple dialog");
                }
            });

            //  setContentView(R.layout.fragment_home);

//        locationEngine = new LostLocationEngine(HomeFragment.this);
//        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
//        locationEngine.setInterval(5000);
//        locationEngine.activate();
//        Location lastLocation = locationEngine.getLastLocation();


    }





    public static class SimpleDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Change Username")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("Later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Later", Toast.LENGTH_SHORT).show();
                        }
                    });

            return builder.create();

        }
    }
}