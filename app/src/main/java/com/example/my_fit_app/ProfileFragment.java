package com.example.my_fit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends AppCompatActivity {
    FirebaseAuth auth;
    Button btnLogout;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fragment);


        auth= FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.logoutbtn);
        user= auth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_fragment:
                                // Handle click on home item
                                startActivity(new Intent(ProfileFragment.this, HomeFragement.class));
                                return true;
                            case R.id.fitness_fragment:
                                // Handle click on fitness item
                                startActivity(new Intent(ProfileFragment.this, FitnessFragement.class));
                                return true;
                            case R.id.profile_fragment:
                                // Handle click on profile item
                                startActivity(new Intent(ProfileFragment.this, ProfileFragment.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }
}