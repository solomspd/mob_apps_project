package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.AUC.mob_apps_project.Common.CurrentUser;
import com.AUC.mob_apps_project.Model.UsersClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button login,register;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loading_login);

        if(fAuth.getCurrentUser() != null){
            check();
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(homeIntent);
                finish();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if(password.length()<=6){
                    mPassword.setError("Password length must be > than six characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

//                FIREBASE
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           check();

                        }else {
                            Toast.makeText(LoginActivity.this,"ERROR\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

    }
    void check(){
         Toast.makeText(LoginActivity.this, "Verifying login...", Toast.LENGTH_LONG).show();

        FirebaseDatabase.getInstance().getReference("/Users/"+fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersClass x = dataSnapshot.getValue(UsersClass.class);
                CurrentUser.user = x;
                if(x.auth.equals("restaurant")) {
//                    Toast.makeText(LoginActivity.this, x.auth, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, control_panel.class);
                    intent.putExtra("rest", x.auth);
                    startActivity(intent);
                } else  if(x.auth.equals("user")) {
//                    Toast.makeText(LoginActivity.this, x.auth, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
