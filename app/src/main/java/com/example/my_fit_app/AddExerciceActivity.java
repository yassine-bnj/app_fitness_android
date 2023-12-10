package com.example.my_fit_app;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class AddExerciceActivity extends AppCompatActivity {
//
//    private EditText txtNameEx;
//    private EditText txtnbseries;
//    private EditText txtnbrepetitions;
//    private EditText txtzonecible;
//    private Button addExButton;
//
//    private String categoryKey;  // Declare a variable to store the category key
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_exercice);
//
//        // Retrieve the Category key from the intent
//        categoryKey = getIntent().getStringExtra("categoryKey");
//
//        // Initialize your UI components
//        txtNameEx = findViewById(R.id.txtNameEx);
//        txtnbseries = findViewById(R.id.txtnbseries);
//        txtnbrepetitions = findViewById(R.id.txtnbrepetiotions);
//        txtzonecible = findViewById(R.id.txtzonecible);
//        addExButton = findViewById(R.id.addEx);
//
//        // Set an onClickListener for the addExButton
//        addExButton.setOnClickListener(v -> {
//            // Get the values from your UI components
//            String name = txtNameEx.getText().toString();
//            int nbSeries = Integer.parseInt(txtnbseries.getText().toString());
//            int nbRepetitions = Integer.parseInt(txtnbrepetitions.getText().toString());
//            String targetZone = txtzonecible.getText().toString();
//
//            // Create a new Exercice object
//            Exercice exercice = new Exercice(name, "", nbSeries, nbRepetitions, targetZone);
//
//            // Get a reference to the Firebase database
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");
//
//            // Update the specific Category with the new Exercice
//            databaseReference.child(categoryKey).child("exercises").push().setValue(exercice);
//
//            // Display a success message
//            Toast.makeText(AddExerciceActivity.this, "Exercise added successfully", Toast.LENGTH_SHORT).show();
//        });
//    }
//}
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddExerciceActivity extends AppCompatActivity {

    private EditText txtNameEx;
    private EditText txtnbseries;
    private EditText txtnbrepetitions;
    private EditText txtzonecible;
    private Button addExButton;
    private ImageView uploadImage;
    private ProgressBar progressBar;

    private String categoryKey;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercice);

        // Retrieve the Category key from the intent
        categoryKey = getIntent().getStringExtra("categoryKey");

        // Initialize your UI components
        txtNameEx = findViewById(R.id.txtNameEx);
        txtnbseries = findViewById(R.id.txtnbseries);
        txtnbrepetitions = findViewById(R.id.txtnbrepetiotions);
        txtzonecible = findViewById(R.id.txtzonecible);
        addExButton = findViewById(R.id.addEx);
        uploadImage = findViewById(R.id.uploadImageEx);
        progressBar = findViewById(R.id.progressBar);

        // Set an onClickListener for the addExButton
        addExButton.setOnClickListener(v -> {
            // Get the values from your UI components
            String name = txtNameEx.getText().toString();
            int nbSeries = Integer.parseInt(txtnbseries.getText().toString());
            int nbRepetitions = Integer.parseInt(txtnbrepetitions.getText().toString());
            String targetZone = txtzonecible.getText().toString();

            // Check if an image is selected
            if (imageUri != null) {
                // Upload the image to Firebase Storage
                uploadImage(name, nbSeries, nbRepetitions, targetZone);
            } else {
                // If no image is selected, create and save the Exercice without an image
                Exercice exercice = new Exercice(name, "", nbSeries, nbRepetitions, targetZone);
                saveExerciceToDatabase(exercice);
            }
        });

        // Set up image selection
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(AddExerciceActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Set up click listener for image selection
        uploadImage.setOnClickListener(v -> {
            Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
            photoPicker.setType("image/*");
            activityResultLauncher.launch(photoPicker);
        });
    }

    private void uploadImage(String name, int nbSeries, int nbRepetitions, String targetZone) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("exercise_images");
        StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + "jpg");

        // Upload the image
        imageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Create and save the Exercice with the image URL
                        Exercice exercice = new Exercice(name, uri.toString(), nbSeries, nbRepetitions, targetZone);
                        saveExerciceToDatabase(exercice);
                    });
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(AddExerciceActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                })
                .addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE));
    }

    private void saveExerciceToDatabase(Exercice exercice) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");

        // Update the specific Category with the new Exercice
        databaseReference.child(categoryKey).child("exercises").push().setValue(exercice);

        progressBar.setVisibility(View.INVISIBLE);

        // Display a success message
        Toast.makeText(AddExerciceActivity.this, "Exercise added successfully", Toast.LENGTH_SHORT).show();
    }
}
