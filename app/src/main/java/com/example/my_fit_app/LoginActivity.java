package com.example.my_fit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//public class LoginActivity extends AppCompatActivity {
//
//    EditText etLoginEmail;
//    EditText etLoginPassword;
//    TextView tvRegisterHere;
//    Button btnLogin;
//
//    FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        etLoginEmail = findViewById(R.id.etLoginEmail);
//        etLoginPassword = findViewById(R.id.etLoginPass);
//        tvRegisterHere = findViewById(R.id.tvRegisterHere);
//        btnLogin = findViewById(R.id.btnLogin);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        btnLogin.setOnClickListener(view -> {
//            loginUser();
//        });
//        tvRegisterHere.setOnClickListener(view ->{
//            startActivity(new Intent(LoginActivity.this, Register.class));
//        });
//    }
//
//    private void loginUser(){
//        String email = etLoginEmail.getText().toString();
//        String password = etLoginPassword.getText().toString();
//
//        if (TextUtils.isEmpty(email)){
//            etLoginEmail.setError("Email cannot be empty");
//            etLoginEmail.requestFocus();
//        }else if (TextUtils.isEmpty(password)){
//            etLoginPassword.setError("Password cannot be empty");
//            etLoginPassword.requestFocus();
//        }else{
//            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, HomeFragement.class));
//                    }else{
//                        Toast.makeText(LoginActivity.this, "Invalid credentials " , Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//}


public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail;
    EditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);






        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar= findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, Register.class));
        });
    }

    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        if ("coach1@gmail.com".equals(email)) {
                            saveUserEmail(email);
                            Toast.makeText(LoginActivity.this, "Welcome Coach!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, CoachDashboardActivity.class));
                            finish();
                        } else {
                            saveUserEmail(email);
                            Toast.makeText(LoginActivity.this, "Welcome Athlete!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeFragement.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("email", email);
        myEdit.commit();
    }



}
