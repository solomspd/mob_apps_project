package com.AUC.mob_apps_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;


import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPassword2,mPhone,VERF_CODE;
    Button verify,submit,send,back;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    boolean phone_is_verified;
    String codeSent;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPassword2 = findViewById(R.id.password2);
        mPhone = findViewById(R.id.phone);
        submit = findViewById(R.id.reg_submit);
        verify = findViewById(R.id.reg_verify);
        back = findViewById(R.id.reg_back);
        send = findViewById(R.id.reg_send);
        VERF_CODE = findViewById(R.id.code);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loading);
        phone_is_verified = false;

        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.verf).setVisibility(View.GONE);
        findViewById(R.id.phone_verified).setVisibility(View.GONE);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    verifySignInCode();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = mFullname.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String password2 = mPassword2.getText().toString().trim();
                final String phone = "+20" + mPhone.getText().toString();

                // Full Name Verification
                if(TextUtils.isEmpty(fullname)){
                    mFullname.setError("Full name is required.");
                    return;
                }
                if(!fullname.matches("^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$")) {
                    mFullname.setError("Full name must be letters only.");
                    return;
                }
                // Email Verification
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Invalid Email.");
                    return;
                }
                // Password Verification
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if(password.length()<=6){
                    mPassword.setError("Password length must be > than six characters.");
                    return;
                }
                Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
                if(!PASSWORD_PATTERN.matcher(password).matches()){
                    mPassword.setError("Invalid password, must have atleast any: letters (caps and small), symbols, and numbers");
                    return;
                }
                if(!password.equals(password2)){
                    mPassword2.setError("Passwords do not match");
                    return;
                }
                // Password Verification
                if(!phone_is_verified){
                    mPhone.setError("Phone is not verified yet.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

//                FIREBASE
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           UsersClass user = new UsersClass(fullname, email, phone);

                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   progressBar.setVisibility(View.GONE);
                                   if (task.isSuccessful()) {
                                       Toast.makeText(RegisterActivity.this,"User Created Successfully.",Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                   } else {
                                       Toast.makeText(RegisterActivity.this,"ERROR\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                   }
                               }
                           });
                       }else {
                           Toast.makeText(RegisterActivity.this,"ERROR\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.INVISIBLE);

                       }
                    }
                });
            }
        });
    }
    private void verifySignInCode(){
        String code = VERF_CODE.getText().toString().trim();

        if(code.isEmpty())
            Toast.makeText(RegisterActivity.this,"Nothing to validate", Toast.LENGTH_SHORT).show();
        else {
            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                signInWithPhoneAuthCredential(credential);

            } catch (Exception e) {
                Toast.makeText(RegisterActivity.this, "Verification Error:\n" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            RemoveUser();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),"Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    private void sendVerificationCode(){

        String phone = "+20" + mPhone.getText().toString();

        if(phone.isEmpty()){
            mPhone.setError("Phone is empty.");
            return;
        }

        if(phone.length() < 10 || TextUtils.isDigitsOnly(phone)){
            mPhone.setError("Invalid phone");
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks




    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(getApplicationContext(), "Verification Completed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RegisterActivity.this,"ERROR\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
            Toast.makeText(getApplicationContext(), "Code Sent", Toast.LENGTH_LONG).show();
            LinearLayout verf = (LinearLayout) findViewById(R.id.verf);
            verf.setVisibility(View.VISIBLE);

        }
    };
    void RemoveUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            phone_is_verified = true;
                            Toast.makeText(getApplicationContext(),"Phone Verified", Toast.LENGTH_LONG).show();
                            (findViewById(R.id.verf)).setVisibility(View.GONE);
                            (findViewById(R.id.num)).setVisibility(View.GONE);
                            TextView txt = findViewById(R.id.phone_verified);
                            txt.setText("+20" + mPhone.getText().toString());
                            (findViewById(R.id.phone_verified)).setVisibility(View.VISIBLE);


                        }
                    }
                });

    }
}
