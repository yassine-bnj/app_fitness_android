package com.example.my_fit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class exerciceDetailsForUser extends AppCompatActivity  {

    FirebaseAuth auth;

    FirebaseUser user;
    private TextView txtNameEx,txtnbseries,txtnbrepetitions,txtzonecible,txtdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice_details_for_user);

        // Retrieve data from Intent
        String exerciceKey = getIntent().getStringExtra("exercice_key");
        String exerciceName = getIntent().getStringExtra("exercice_name");
        int exerciceNbSeries = getIntent().getIntExtra("exercice_nbseries", 0);
        int exerciceNbRepetitions = getIntent().getIntExtra("exercice_nbrepetitions", 0);
        String exerciceDescription = getIntent().getStringExtra("exercice_description");
        String exerciceYtbLink = getIntent().getStringExtra("exercice_ytblink");
        String musclecible = getIntent().getStringExtra("muscle_cible");
        //   String exerciceImageURL = getIntent().getStringExtra("exercice_image");

        // Find TextViews in the layout
        txtNameEx = findViewById(R.id.NameExercice);
        txtnbseries = findViewById(R.id.nomreseries);
        txtnbrepetitions = findViewById(R.id.nombrerep);
        txtzonecible = findViewById(R.id.targetArea);
        txtdescription = findViewById(R.id.DescriptionExercice);




        // Set the text of the TextViews
        txtNameEx.setText(exerciceName);
        txtnbseries.setText(String.valueOf(exerciceNbSeries));
        txtnbrepetitions.setText(String.valueOf(exerciceNbRepetitions));
        txtzonecible.setText(musclecible);
        txtdescription.setText(exerciceDescription);


    }

    //    visitLink
    public void visitLink(View view) {
        String link = getIntent().getStringExtra("exercice_ytblink");
        if (!link.contains("http")){
            link = "https://"+link;
        }





        // Get the link from the TextView
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);


    }
    //goback to the previous activity
    public void goBack(View view) {
        finish();

    }
    //addtoprogram
    public void addtoprogram(View view) {
        // Get the current user's email
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Log the exercise key
        System.out.print(getIntent().getStringExtra("exercice_key"));

        // Create an Exercice object from the intent data
        Exercice exercice = new Exercice(
                getIntent().getStringExtra("exercice_name"),
                getIntent().getStringExtra("exercice_image"),
                getIntent().getIntExtra("exercice_nbseries", 0),
                getIntent().getIntExtra("exercice_nbrepetitions", 0),
                getIntent().getStringExtra("muscle_cible"),
                getIntent().getStringExtra("exercice_description"),
                getIntent().getStringExtra("exercice_ytblink")
        );

        ///get all the programs
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Programs");
        Task<DataSnapshot> task = databaseReference.get();
        task.addOnSuccessListener(dataSnapshot -> {
                    //get all the programs
                    for (DataSnapshot programSnapshot : dataSnapshot.getChildren()) {
                        Program program = programSnapshot.getValue(Program.class);
                        //get the program of the current user
                        if (program.getOwnerEmail().equals(currentUserEmail)) {

                            String Key = programSnapshot.getKey();
                            System.out.println("Key");
                            System.out.println(Key);
                            System.out.println("Exercice");
                            System.out.println(exercice.toString());

                            databaseReference.child(Key).child("exercises").push().setValue(exercice);

                            Toast.makeText(exerciceDetailsForUser.this, "Exercise added to the program", Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                }
        );
    }


        //show all the programs
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //get all the programs
//                for (DataSnapshot programSnapshot : dataSnapshot.getChildren()) {
//                    Program program = programSnapshot.getValue(Program.class);
//                    //get the program of the current user
//                    if (program.getOwnerEmail().equals(currentUserEmail)) {
//
//                        String Key = programSnapshot.getKey();
//                        System.out.println("Key");
//                        System.out.println(Key);
//                        System.out.println("Exercice");
//                        System.out.println(exercice.toString());
//
//                        databaseReference.child(Key).child("exercises").push().setValue(exercice);
//
//                        Toast.makeText(exerciceDetailsForUser.this, "Exercise added to the program", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }
//                }
//
//
//              //  Toast.makeText(exerciceDetailsForUser.this, "Exercise added to the program", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle errors
//                Toast.makeText(exerciceDetailsForUser.this, "Failed to retrieve programs", Toast.LENGTH_SHORT).show();
//            }
//        });









    }