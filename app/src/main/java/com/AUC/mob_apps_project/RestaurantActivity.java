package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Common.CurrentRestaurant;
import com.AUC.mob_apps_project.Database.Database;
import com.AUC.mob_apps_project.Common.CurrentUser;
import com.AUC.mob_apps_project.Model.Rating;
import com.AUC.mob_apps_project.Model.RestaurantClass;
import com.AUC.mob_apps_project.Model.UsersClass;
import com.AUC.mob_apps_project.ui.Navigation.Navigationn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class RestaurantActivity extends AppCompatActivity implements RatingDialogListener {
    String Rest_Name="";
    FloatingActionButton btnRating;
    RatingBar ratingBar;
    FirebaseDatabase database;
    DatabaseReference ratingtbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);


        Rest_Name = getIntent().getStringExtra("Restaurant");
        database = FirebaseDatabase.getInstance();
        ratingtbl = database.getReference("Rating");


        btnRating = (FloatingActionButton) findViewById(R.id.btn_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        getRating(Rest_Name);

        TextView food_description = findViewById(R.id.food_description);
        food_description.setText(CurrentRestaurant.restaurant.description);

        TextView menu_name = findViewById(R.id.menu_name);
        menu_name.setText(CurrentRestaurant.restaurant.name);


        ImageView img = findViewById(R.id.menu_image);
//        Picasso.get().load(CurrentRestaurant.restaurant.image).into(img);
        Picasso.get().load(CurrentRestaurant.restaurant.image).into(img);


        TextView food_description2 = findViewById(R.id.food_description2);
        food_description2.setText("City: "+CurrentRestaurant.restaurant.city+"\nPrice Range: "+CurrentRestaurant.restaurant.price_range);


        Button button = findViewById(R.id.menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new Database(getBaseContext()).cleanCart(); // Empty Cart
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            i.putExtra("Restaurant", Rest_Name);
            startActivity(i);
            }
        });

        Button rating = findViewById(R.id.comments);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).cleanCart(); // Empty Cart
                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.putExtra("Restaurant", Rest_Name);
                startActivity(i);
            }
        });

        Button booking = findViewById(R.id.booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).cleanCart(); // Empty Cart
                Intent i = new Intent(getApplicationContext(), table_sel.class);
                i.putExtra("Restaurant", Rest_Name);
                startActivity(i);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Directions Map To Restaurant",Toast.LENGTH_SHORT).show();
                Navigate();
            }
        });
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating();
            }
        });
    }

    private void getRating(String rest_name) {

        com.google.firebase.database.Query foodRating = ratingtbl.orderByChild("restaurant").equalTo(rest_name);
        foodRating.addValueEventListener(new ValueEventListener() {
            int c=0,s=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Rating item = postSnapshot.getValue(Rating.class);
                    s+=Integer.parseInt(item.getValue());
                    c++;
                }
                if(c!=0){
                float avg = s/c;ratingBar.setRating(avg);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showRating() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite Okay","Very Good","Excellent"))
                .setDefaultRating(1).setTitle("Rate this food").setDescription("Please select some stars and write your feedback")
                .setTitleTextColor(R.color.colorPrimary).setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comments here...").setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark).setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(RestaurantActivity.this).show();
    }


    public void Navigate()
    {
        Intent intent = new Intent(this, Navigationn.class);


        startActivity(intent);

    }


    @Override
    public void onNegativeButtonClicked() {


    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {
        final Rating rating = new Rating(CurrentUser.user.fullname,Rest_Name,String.valueOf(i),s);
        ratingtbl.push().setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RestaurantActivity.this,"Thank you for your feedback!",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
