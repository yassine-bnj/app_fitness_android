package com.example.my_fit_app;

//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//public class AddDialog extends AppCompatActivity{
//    private Button addCategbtn;
//    private ImageView uploadImage;
//    EditText nameCateg;
//    ProgressBar progressBar;
//    private Uri imageUri;
//    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");
//    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_dialog);
//
//        uploadImage = findViewById(R.id.uploadImage);
//        nameCateg = findViewById(R.id.txtNameCateg);
//        addCategbtn = findViewById(R.id.addCateg);
//        progressBar = findViewById(R.id.progressBar);
//
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK){
//                            Intent data = result.getData();
//                            imageUri = data.getData();
//                            uploadImage.setImageURI(imageUri);
//                        } else {
//                            Toast.makeText(AddDialog.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });
//
//        addCategbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imageUri != null){
//                    uploadToFirebase(imageUri);
//                } else  {
//                    Toast.makeText(AddDialog.this, "Please select image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    //Outside onCreate
//    private void uploadToFirebase(Uri uri){
//        String name = nameCateg.getText().toString();
//        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Category dataClass = new Category(name,uri.toString());
//                        String key = databaseReference.push().getKey();
//                        databaseReference.child(key).setValue(dataClass);
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(AddDialog.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddDialog.this, CoachDashboardActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(AddDialog.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private String getFileExtension(Uri fileUri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
//    }
//}
//

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddDialog extends AppCompatActivity {
    private Button addCategbtn;
    private ImageView uploadImage;
    EditText nameCateg;
    ProgressBar progressBar;
    private Uri imageUri;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        uploadImage = findViewById(R.id.uploadImage);
        nameCateg = findViewById(R.id.txtNameCateg);
        addCategbtn = findViewById(R.id.addCateg);
        progressBar = findViewById(R.id.progressBar);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(AddDialog.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        addCategbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                } else {
                    Toast.makeText(AddDialog.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Outside onCreate
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDialog.this);
        builder.setTitle("Success");
        builder.setMessage("Category added successfully");
        builder.setIcon(android.R.drawable.ic_dialog_info); // Set your desired icon here

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the OK button click if needed
                Intent intent = new Intent(AddDialog.this, CoachDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }

    private void uploadToFirebase(Uri uri) {
        String name = nameCateg.getText().toString();
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Create a Category object
                        Category category = new Category(name, uri.toString());

                        // Push the category object to Firebase Realtime Database
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).setValue(category);

                        progressBar.setVisibility(View.INVISIBLE);

                        // Show the success dialog instead of a toast
                        showSuccessDialog();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddDialog.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
