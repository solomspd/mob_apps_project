package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.AUC.mob_apps_project.Model.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import timber.log.Timber;

public class menu_item_edit extends AppCompatActivity {

    DatabaseReference ref;
    EditText name, price, desc, diss;
    Spinner cat;
    ImageView pic;
    ArrayAdapter<String> spin_adapter;
    ArrayList<String> cat_list;
    Button save;
    String pic_uri;
    StorageReference store_pic;
    final int get_pic = 3;
    String rest;
    String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_edit);

        rest = getIntent().getStringExtra("RestId");
        cat_id = getIntent().getExtras().getString("cat");

        ref = FirebaseDatabase.getInstance().getReference("/Restaurant").child(rest).child("Food").child(getIntent().getStringExtra("FoodId"));

        name = findViewById(R.id.item_name);
        price = findViewById(R.id.item_price);
        desc = findViewById(R.id.item_description);
        pic = findViewById(R.id.item_pic);
        diss = findViewById(R.id.item_disscount);
        cat = findViewById(R.id.item_cat);

        cat_list = get_cat();

        spin_adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cat_list);

        cat.setAdapter(spin_adapter);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    name.setText((String) dataSnapshot.child("name").getValue());
                    price.setText((String) dataSnapshot.child("price").getValue());
                    pic_uri = (String) dataSnapshot.child("image").getValue();
                    Picasso.get().load(pic_uri).into(pic);
                    diss.setText((String) dataSnapshot.child("discount").getValue());
                    desc.setText((String) dataSnapshot.child("description").getValue());
                    cat.setSelection(Integer.parseInt((String) dataSnapshot.child("menuId").getValue())-1);
                } catch (Exception err) {
                    Timber.e(err);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.toException(), "onCancelled: ");
            }

        });

        findViewById(R.id.item_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("name").setValue(name.getText().toString());
                ref.child("price").setValue(price.getText().toString());
                ref.child("discount").setValue(diss.getText().toString());
                ref.child("menuId").setValue("0"+String.valueOf(cat.getSelectedItemPosition()+1));
                ref.child("description").setValue(desc.getText().toString());
                ref.child("image").setValue(pic_uri);

                leave();
            }
        });

        findViewById(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue();
                leave();
            }
        });

        findViewById(R.id.item_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, get_pic);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if (requestCode == get_pic && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Picasso.get().load(selectedImage).into(pic);
            store_pic = FirebaseStorage.getInstance().getReference(selectedImage.getPath().substring(selectedImage.getPath().lastIndexOf('/'))+".jpg");
            store_pic.putFile(selectedImage);
            store_pic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    pic_uri = uri.toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
    }

    private void leave() {
        Intent intent = new Intent(menu_item_edit.this, FoodActivity.class);
        intent.putExtra("owner", true);
        intent.putExtra("Name", cat.getSelectedItemPosition());
        intent.putExtra("Title", cat.getSelectedItem().toString());
        intent.putExtra("RestId", rest);
        intent.putExtra("Name", cat_id);
        startActivity(intent);
    }

    private ArrayList<String> get_cat() {
        final ArrayList<String> ret = new ArrayList<>();
        DatabaseReference cat_ref = ref.getParent().getParent().child("Category");
        cat_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    ret.add((String)next.child("name").getValue());
                }
                cat_list = ret;
                try {
                    spin_adapter.notifyDataSetChanged();
                } catch (Exception err) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return ret;
    }
}
