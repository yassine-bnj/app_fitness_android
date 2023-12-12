package com.example.my_fit_app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

//public class EditCategory extends AppCompatActivity {
//
//    ImageView updateImage;
//    Button updateButton;
//    EditText updatename;
//    String nameCat;
//    String imageUrl;
//    String key, oldImageURL;
//    Uri uri;
//    DatabaseReference databaseReference;
//    StorageReference storageReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_category);
//
//        updateButton = findViewById(R.id.editCateg);
//        updatename = findViewById(R.id.updateName);
//        updateImage = findViewById(R.id.updateuploadImage);
//
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK){
//                            Intent data = result.getData();
//                            uri = data.getData();
//                            updateImage.setImageURI(uri);
//                        } else {
//                            Toast.makeText(EditCategory.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            Glide.with(EditCategory.this).load(bundle.getString("Image")).into(updateImage);
//                updatename.setText(bundle.getString("Name"));
//
//            key = bundle.getString("Key");
//            oldImageURL = bundle.getString("Image");
//        }
//        databaseReference = FirebaseDatabase.getInstance().getReference("Category").child(key);
//
//        updateImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });
//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveData();
//                Intent intent = new Intent(EditCategory.this, CoachDashboardActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//    public void saveData(){
//        storageReference = FirebaseStorage.getInstance().getReference().child("Category").child(uri.getLastPathSegment());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(EditCategory.this);
//        builder.setCancelable(false);
//        builder.setView(R.layout.progress);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete());
//                Uri urlImage = uriTask.getResult();
//                imageUrl = urlImage.toString();
//                updateData();
//                dialog.dismiss();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                dialog.dismiss();
//            }
//        });
//    }
//    public void updateData(){
//        nameCat = updatename.getText().toString().trim();
//
//
//        Category dataClass = new Category(nameCat,imageUrl);
//
//        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
//                    reference.delete();
//                    Toast.makeText(EditCategory.this, "Updated", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(EditCategory.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditCategory extends AppCompatActivity {

    ImageView updateImage;
    Button updateButton;
    EditText updateName;
    String name;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        updateButton = findViewById(R.id.editCateg);
        updateName = findViewById(R.id.updateName);
        updateImage = findViewById(R.id.updateuploadImage);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(EditCategory.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(EditCategory.this).load(bundle.getString("photoURL")).into(updateImage);
            updateName.setText(bundle.getString("categoryName"));

            key = bundle.getString("Key");
            oldImageURL = bundle.getString("photoURL");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Category").child(key);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
//                Intent intent = new Intent(EditCategory.this, CoachDashboardActivity.class);
//                startActivity(intent);
            }
        });
    }
    public void saveData() {
        if (uri == null) {
            Toast.makeText(EditCategory.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        storageReference = FirebaseStorage.getInstance().getReference().child("Category").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(EditCategory.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(EditCategory.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(){
        name = updateName.getText().toString().trim();


        Category category = new Category(name, imageUrl);

        databaseReference.setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(EditCategory.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditCategory.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}