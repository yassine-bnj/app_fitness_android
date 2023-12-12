package com.example.my_fit_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice_details);

        // Retrieve data from Intent
        String exerciceKey = getIntent().getStringExtra("exercice_key");
        String exerciceName = getIntent().getStringExtra("exercice_name");
        int exerciceNbSeries = getIntent().getIntExtra("exercice_nbseries", 0);
        int exerciceNbRepetitions = getIntent().getIntExtra("exercice_nbrepetitions", 0);
        String exerciceDescription = getIntent().getStringExtra("exercice_description");
        String exerciceYtbLink = getIntent().getStringExtra("exercice_ytblink");
        String exerciceImageURL = getIntent().getStringExtra("exercice_image");

        // Find TextViews in the layout
//        TextView nameView = findViewById(R.id.nameView);
//        TextView muscleCibleView = findViewById(R.id.muscleCibleView);
//        TextView nbRepetitionView = findViewById(R.id.nbRepetitionView);

        // Set data to TextViews
//        nameView.setText(exerciceName);
//        muscleCibleView.setText("Muscle cible: " + exerciceDescription);
//        nbRepetitionView.setText("Number of Repetitions: " + exerciceNbRepetitions);

        // Add more TextViews and set data accordingly
    }
}
