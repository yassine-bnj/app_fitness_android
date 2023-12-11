package com.example.my_fit_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity  implements MyExerciceAdapter.OnItemClickListener{

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
                Toast.makeText(WorkoutActivity.this, "Failed to retrieve exercises", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
//        if (position >= 0 && position < exerciceAdapter.getItemCount()) {
//            Exercice exercice = exerciceAdapter.getExerciceList().get(position);
//
//            if (exercice != null) {
//                String exerciceKey = exercice.getKey();
//                String imageUrl = exercice.getImageURLEx();
//
//                // Build the confirmation dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
//                builder.setTitle("Confirm Deletion");
//                builder.setMessage("Are you sure you want to delete this exercise?");
//                builder.setPositiveButton("Yes", (dialog, which) -> {
//                    // User clicked Yes, proceed with deletion
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category")
//                            .child(categoryKey) // Specify the category key
//                            .child("exercises") // Specify the exercises node
//                            .child(exerciceKey); // Specify the exercice key
//
//                    FirebaseStorage storage = FirebaseStorage.getInstance();
//
//                    if (imageUrl != null && !imageUrl.isEmpty()) {
//                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
//                        storageReference.delete().addOnSuccessListener(unused -> {
//                            reference.removeValue();  // Remove the exercise from the database
//                            Toast.makeText(WorkoutActivity.this, "Exercise Deleted", Toast.LENGTH_SHORT).show();
//                        }).addOnFailureListener(e -> {
//                            // Handle deletion failure
//                            Toast.makeText(WorkoutActivity.this, "Failed to delete exercise", Toast.LENGTH_SHORT).show();
//                        });
//                    }
//                });
//                builder.setNegativeButton("No", (dialog, which) -> {
//                    // User clicked No, do nothing or provide feedback if needed
//                    Toast.makeText(WorkoutActivity.this, "Deletion canceled", Toast.LENGTH_SHORT).show();
//                });
//
//                // Show the confirmation dialog
//                builder.create().show();
//            }
//        }
    }

    @Override
    public void onExerciceItemClick(int position) {
        Exercice selectedExercice = exerciceAdapter.getExerciceList().get(position);

        Intent detailIntent = new Intent(WorkoutActivity.this, ExerciceDetailsActivity.class);
        detailIntent.putExtra("exercice_key", selectedExercice.getKey());
        detailIntent.putExtra("exercice_name", selectedExercice.getNomExercice());
        detailIntent.putExtra("exercice_nbseries", selectedExercice.getNbserie());
        detailIntent.putExtra("exercice_nbrepetitions", selectedExercice.getNbrepetition());
        detailIntent.putExtra("exercice_description", selectedExercice.getDescription());
        detailIntent.putExtra("exercice_ytblink", selectedExercice.getYtblink());
        detailIntent.putExtra("exercice_image", selectedExercice.getImageURLEx());

        startActivity(detailIntent);
    }


}
