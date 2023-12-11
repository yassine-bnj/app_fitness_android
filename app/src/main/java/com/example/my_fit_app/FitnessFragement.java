package com.example.my_fit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FitnessFragement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_fragement);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBarF);



        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_fragment:
                                // Handle click on home item
                                startActivity(new Intent(FitnessFragement.this, HomeFragement.class));
                                return true;
                            case R.id.fitness_fragment:
                                // Handle click on fitness item
                                startActivity(new Intent(FitnessFragement.this, FitnessFragement.class));
                                return true;
                            case R.id.profile_fragment:
                                // Handle click on profile item
                                startActivity(new Intent(FitnessFragement.this, ProfileFragment.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }

    public void goToCalulateImc(View view){


    }
    public void goToMaps(View view){
        startActivity(new Intent(FitnessFragement.this, maps.class));


    }
    //go to imc activity
    public void goToImc(View view) {
        startActivity(new Intent(FitnessFragement.this, BmiCalc.class));
    }

}