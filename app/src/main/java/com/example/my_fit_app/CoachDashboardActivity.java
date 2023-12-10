package com.example.my_fit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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


//public class CoachDashboardActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    List<Category> dataList;
//    DatabaseReference databaseReference;
//    ValueEventListener eventListener;
//    MyAdapter adapter;
//    ImageButton btnDelete;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coach_dashboard);
//
//
//        recyclerView = findViewById(R.id.recyclerView);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(CoachDashboardActivity.this,1);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        AlertDialog.Builder builder= new AlertDialog.Builder(CoachDashboardActivity.this);
//        builder.setCancelable(false);
//        builder.setView(R.layout.progress);
//        AlertDialog dialog = builder.create();
//
//        dataList = new ArrayList<>();
//         adapter = new MyAdapter(CoachDashboardActivity.this,dataList);
//        recyclerView.setAdapter(adapter);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
//        dialog.show();
//
//        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataList.clear();
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
//                    Category category = itemSnapshot.getValue(Category.class);
//                    category.setKey(itemSnapshot.getKey());
//                    dataList.add(category);
//                }
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                dialog.dismiss();
//
//            }
//        });
//
//
//
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.home_fragment:
//                                // Handle click on home item
//                                startActivity(new Intent(CoachDashboardActivity.this, CoachDashboardActivity.class));
//                                return true;
//                            case R.id.add_fragment:
//                                startActivity(new Intent(CoachDashboardActivity.this, AddDialog.class));
//                                return true;
//                            case R.id.profile_fragment:
//                                // Handle click on profile item
//                                startActivity(new Intent(CoachDashboardActivity.this, ProfileFragment.class));
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//    }
//}
public class CoachDashboardActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private List<Category> dataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();

        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        // Set the listener in your adapter
        adapter.setOnItemClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Category category = itemSnapshot.getValue(Category.class);
                    if (category != null) {
                        category.setKey(itemSnapshot.getKey());
                        dataList.add(category);
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_fragment:
                    // Handle click on home item
                    startActivity(new Intent(this, CoachDashboardActivity.class));
                    return true;
                case R.id.add_fragment:
                    startActivity(new Intent(this, AddDialog.class));
                    return true;
                case R.id.profile_fragment:
                    // Handle click on profile item
                    startActivity(new Intent(this, ProfileFragment.class));
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < dataList.size()) {
            Category category = dataList.get(position);

            if (category != null) {
                String key = category.getKey();
                String imageUrl = category.getImageURL();

                // Build the confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CoachDashboardActivity.this);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    // User clicked Yes, proceed with deletion
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(unused -> {
                            reference.child(key).removeValue();
                            Toast.makeText(CoachDashboardActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    // User clicked No, do nothing or provide feedback if needed
                    Toast.makeText(CoachDashboardActivity.this, "Deletion canceled", Toast.LENGTH_SHORT).show();
                });

                // Show the confirmation dialog
                builder.create().show();
            }
        }
    }


    @Override
    public void onEditClick(int position) {
        if (position >= 0 && position < dataList.size()) {
            Category category = dataList.get(position);

            if (category != null) {
                // Create an intent to open the EditCategory activity
                Intent intent = new Intent(this, EditCategory.class);

                // Pass the data of the clicked item to EditCategory activity
                intent.putExtra("Key", category.getKey());

                intent.putExtra("categoryName", category.getCategName());
                intent.putExtra("photoURL", category.getImageURL()); // Add this line to pass the photo URL
                // Start the EditCategory activity
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Category category = dataList.get(position);

        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("key", category.getKey());
        startActivity(intent);
    }


}
