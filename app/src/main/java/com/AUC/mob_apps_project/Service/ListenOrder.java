//package com.AUC.mob_apps_project.Service;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//import com.AUC.mob_apps_project.HomeActivity;
//import com.AUC.mob_apps_project.Model.Request;
//import com.AUC.mob_apps_project.R;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.Random;
//
//public class ListenOrder extends Service implements ChildEventListener {
//
//    FirebaseDatabase database;
//    DatabaseReference orders;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        database = FirebaseDatabase.getInstance();
//        orders = database.getReference("Requests");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        orders.addChildEventListener(this);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    public ListenOrder() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//        Request request = dataSnapshot.getValue(Request.class);
//        if(request.getStatus().equals("0"))
//            showNotification(dataSnapshot.getKey(),request);
//    }
//
//    private void showNotification(String key, Request request) {
//        int rand = new Random().nextInt(9999-1)+1;
//        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL).setTicker("FoodCloud").setContentInfo("New Order").setContentText("You have new order: #"+key)
//                .setSmallIcon(R.mipmap.ic_launcher);
//
//
//        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(rand,builder.build());
//
//    }
//
//    @Override
//    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//    }
//
//    @Override
//    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//    }
//
//    @Override
//    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//}
