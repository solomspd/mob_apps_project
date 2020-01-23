package com.AUC.mob_apps_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AUC.mob_apps_project.Database.Database;
import com.AUC.mob_apps_project.Common.CurrentUser;
import com.AUC.mob_apps_project.Model.Order;
import com.AUC.mob_apps_project.Model.Request;
import com.AUC.mob_apps_project.Payment.Config.Config;
import com.AUC.mob_apps_project.Payment.PaymentDetails;
import com.AUC.mob_apps_project.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace,btnEmpty;
    String Rest_ID="";
    String amount ="";

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onDestroy()
    {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

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

        Intent intent= new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order e = new Order();
                Request request = new Request(CurrentUser.user.phone,Rest_ID,txtTotalPrice.getText().toString(),CurrentUser.user.fullname,cart,"0");
                if(!cart.isEmpty()) {
                    String time = String.valueOf(System.currentTimeMillis());
                    requests.child(time).setValue(request); // Submit
                    processPayment();
//                    requests.child(time).setValue(cart);
                    new Database(getBaseContext()).cleanCart();                 // Delete Cart
                    Toast.makeText(CartActivity.this,"Thank you, Checking out..", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                    finish();
                }
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


    private void processPayment(){
        amount = (txtTotalPrice.getText().toString()).replaceAll("[^\\d.]", "");
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal((String.valueOf(amount))),"USD" +
                "", "Payment", PayPalPayment.PAYMENT_INTENT_SALE );
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null )
                {
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount",amount));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
            }}
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

}
