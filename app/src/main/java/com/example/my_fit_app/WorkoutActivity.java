package com.example.my_fit_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    private String categoryKey;
    private RecyclerView recyclerView;
    private MyExerciceAdapter exerciceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // Retrieve the key from the intent
        categoryKey = getIntent().getStringExtra("key");

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciceAdapter = new MyExerciceAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(exerciceAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_fragment:
                    // Handle click on home item
                    startActivity(new Intent(this, WorkoutActivity.class));
                    return true;
                case R.id.add_fragment:
                    // Pass the key to AddExerciceActivity
                    Intent addExerciceIntent = new Intent(this, AddExerciceActivity.class);
                    addExerciceIntent.putExtra("categoryKey", categoryKey);
                    startActivity(addExerciceIntent);
                    return true;
                case R.id.profile_fragment:
                    // Handle click on profile item
                    startActivity(new Intent(this, ProfileFragment.class));
                    return true;
                default:
                    return false;
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
                Toast.makeText(WorkoutActivity.this, "Failed to retrieve exercises", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
