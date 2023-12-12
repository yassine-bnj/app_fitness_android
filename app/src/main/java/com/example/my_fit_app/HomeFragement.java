package com.example.my_fit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragement extends AppCompatActivity  implements CategoriesAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<Category> dataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    private CategoriesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragement);

        if (getEmail() == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }



        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();

        dataList = new ArrayList<>();
        adapter = new CategoriesAdapter(this, dataList);
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
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_fragment:
                                // Handle click on home item
                                startActivity(new Intent(HomeFragement.this, HomeFragement.class));
                                return true;
                            case R.id.fitness_fragment:
                                // Handle click on fitness item
                                startActivity(new Intent(HomeFragement.this, FitnessFragement.class));
                                return true;
                            case R.id.profile_fragment:
                                // Handle click on profile item
                                startActivity(new Intent(HomeFragement.this, profileUser.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void onEditClick(int position) {

    }
    @Override
    public void onItemClick(int position) {
        Category category = dataList.get(position);

        Intent intent = new Intent(this, ExercicesForUser.class);
        intent.putExtra("key", category.getKey());
        startActivity(intent);
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        return email;
    }


}
