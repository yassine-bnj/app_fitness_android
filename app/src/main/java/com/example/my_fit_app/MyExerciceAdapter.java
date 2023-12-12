package com.example.my_fit_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyExerciceAdapter extends RecyclerView.Adapter<MyExerciceAdapter.ExerciceViewHolder>

{

    private Context context;
    private List<Exercice> exerciceList;
    private OnItemClickListener mListener;

    public MyExerciceAdapter(Context context, List<Exercice> exerciceList) {
        this.context = context;
        this.exerciceList = exerciceList;
    }

    public List<Exercice> getExerciceList() {

        return exerciceList;
    }

    // Define an interface to handle button clicks
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onExerciceItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ExerciceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleexercieitem, parent, false);
        return new ExerciceViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceViewHolder holder, int position) {
        Exercice exercice = exerciceList.get(position);

        holder.txtNomExercice.setText(exercice.getNomExercice());
        holder.txtMuscleCible.setText(exercice.getMusclecible());

        Glide.with(context)
                .load(exercice.getImageURLEx())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return exerciceList.size();
    }

    public static class ExerciceViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomExercice, txtMuscleCible, txtNbRepetition, txtNbSerie;
        ImageView imageView;
        ImageButton btnDeleteEx, btnEditEx;

        public ExerciceViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtNomExercice = itemView.findViewById(R.id.recnameEx);
            txtMuscleCible = itemView.findViewById(R.id.reczonecible);

            imageView = itemView.findViewById(R.id.recImageEx);
            btnDeleteEx = itemView.findViewById(R.id.btnDeleteExercice);
//            btnEditEx = itemView.findViewById(R.id.btnEditExercice);

            // Handle button click
            btnDeleteEx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        // Get the position of the item that was clicked
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            // Handle item click
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    System.out.println("onItemClick");
                    System.out.println(listener);
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onExerciceItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setExerciceList(List<Exercice> exerciceList) {
        this.exerciceList = exerciceList;
        notifyDataSetChanged();  // Notify the adapter that the dataset has changed
    }
}
