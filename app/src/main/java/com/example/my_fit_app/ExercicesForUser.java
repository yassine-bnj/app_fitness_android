package com.example.my_fit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExercicesForUser extends AppCompatActivity implements MyExerciceAdapterForUser.OnItemClickListener{
    private String categoryKey;
    private RecyclerView recyclerView;
    private MyExerciceAdapterForUser exerciceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercices_for_user);
        // Retrieve the key from the intent
        categoryKey = getIntent().getStringExtra("key");

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciceAdapter = new MyExerciceAdapterForUser(this, new ArrayList<>());
        recyclerView.setAdapter(exerciceAdapter);

        // Set the listener in your adapter
        exerciceAdapter.setOnItemClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_fragment:
                                // Handle click on home item
                                startActivity(new Intent(ExercicesForUser.this, HomeFragement.class));
                                return true;
                            case R.id.fitness_fragment:
                                // Handle click on fitness item
                                startActivity(new Intent(ExercicesForUser.this, FitnessFragement.class));
                                return true;
                            case R.id.profile_fragment:
                                // Handle click on profile item
                                startActivity(new Intent(ExercicesForUser.this, ProfileFragment.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });


        // Retrieve and display exercises
        retrieveExercises();
    }

    private void retrieveExercises() {
        DatabaseReference exercisesRef = FirebaseDatabase.getInstance().getReference("Category")
                .child(categoryKey)
                .child("exercises");

        exercisesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Exercice> exerciceList = new ArrayList<>();

                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {
                    Exercice exercice = exerciseSnapshot.getValue(Exercice.class);
                    if (exercice != null) {
                        System.out.println(exercice.getImageURLEx());
                        exerciceList.add(exercice);
                    }
                }

                // Update the adapter with the new data
                exerciceAdapter.setExerciceList(exerciceList);
                exerciceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(ExercicesForUser.this, "Failed to retrieve exercises", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onExerciceItemClick(int position) {
        Exercice selectedExercice = exerciceAdapter.getExerciceList().get(position);

        Intent detailIntent = new Intent(ExercicesForUser.this, exerciceDetailsForUser.class);
        detailIntent.putExtra("exercice_key", selectedExercice.getKey());
        detailIntent.putExtra("exercice_name", selectedExercice.getNomExercice());
        detailIntent.putExtra("exercice_nbseries", selectedExercice.getNbserie());
        detailIntent.putExtra("exercice_nbrepetitions", selectedExercice.getNbrepetition());
        detailIntent.putExtra("exercice_description", selectedExercice.getDescription());
        detailIntent.putExtra("exercice_ytblink", selectedExercice.getYtblink());
        detailIntent.putExtra("exercice_image", selectedExercice.getImageURLEx());
        detailIntent.putExtra("muscle_cible", selectedExercice.getMusclecible());

        startActivity(detailIntent);
    }


}