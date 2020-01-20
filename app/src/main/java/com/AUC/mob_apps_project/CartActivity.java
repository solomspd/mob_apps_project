package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Database.Database;
import com.AUC.mob_apps_project.Common.CurrentUser;
import com.AUC.mob_apps_project.Model.Order;
import com.AUC.mob_apps_project.Model.Request;
import com.AUC.mob_apps_project.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace,btnEmpty;
    String Rest_ID="";

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        Rest_ID = getIntent().getStringExtra("Restaurant");
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);
        btnEmpty = (Button) findViewById(R.id.btnEmptyCart);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Request request = new Request(CurrentUser.user.phone,CurrentUser.user.fullname,Rest_ID,txtTotalPrice.getText().toString(),cart);
                // SUBMIT OPTION
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                // Delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this,"Thank you, Order has been placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this,"Cart has been cleared.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        
        loadListFood();
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for(Order order:cart)
            total += (Integer.parseInt(order.Price))*(Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("en","EG");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));

    }
}
