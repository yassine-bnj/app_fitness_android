package com.example.my_fit_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyExerciceAdapter extends RecyclerView.Adapter<MyExerciceAdapter.ExerciceViewHolder> {

    private Context context;
    private List<Exercice> exerciceList;

    public MyExerciceAdapter(Context context, List<Exercice> exerciceList) {
        this.context = context;
        this.exerciceList = exerciceList;
    }

    @NonNull
    @Override
    public ExerciceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleexercieitem, parent, false);
        return new ExerciceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceViewHolder holder, int position) {
        Exercice exercice = exerciceList.get(position);

        holder.txtNomExercice.setText(exercice.getNomExercice());
        holder.txtMuscleCible.setText(exercice.getMusclecible());


        // Load image using Glide
//        Glide.with(context)
//                .load(exercice.getImageURLEx())  // Assuming you have a getImageUrl() method in your Exercice class
//                .into(holder.imageView);  // Assuming you have an ImageView in your layout with the id 'imageView'
    }

    @Override
    public int getItemCount() {
        return exerciceList.size();
    }

    public static class ExerciceViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomExercice, txtMuscleCible, txtNbRepetition, txtNbSerie;
        ImageView imageView;  // Add ImageView for the image

        public ExerciceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomExercice = itemView.findViewById(R.id.recnameEx);
            txtMuscleCible = itemView.findViewById(R.id.reczonecible);

            //imageView = itemView.findViewById(R.id.imageView);  // Initialize ImageView
        }

    }

    public void setExerciceList(List<Exercice> exerciceList) {
        this.exerciceList = exerciceList;
        notifyDataSetChanged();  // Notify the adapter that the dataset has changed
    }
}
