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

public class MyExerciceAdapterForUser extends RecyclerView.Adapter<MyExerciceAdapterForUser.ExerciceViewHolder> {

    private Context context;
    private List<Exercice> exerciceList;
    private MyExerciceAdapterForUser.OnItemClickListener mListener;

    public List<Exercice> getExerciceList() {

        return exerciceList;
    }
    // Define an interface to handle button clicks
    public interface OnItemClickListener {

        void onExerciceItemClick(int position);
    }

    public MyExerciceAdapterForUser(Context context, List<Exercice> exerciceList) {
        this.context = context;
        this.exerciceList = exerciceList;
    }
    public void setOnItemClickListener(MyExerciceAdapterForUser.OnItemClickListener listener) {
        this.mListener = listener;
    }
    @NonNull
    @Override
    public MyExerciceAdapterForUser.ExerciceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercices_item_user, parent, false);
        return new MyExerciceAdapterForUser.ExerciceViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyExerciceAdapterForUser.ExerciceViewHolder holder, int position) {
        Exercice exercice = exerciceList.get(position);

        holder.txtNomExercice.setText(exercice.getNomExercice());
       // holder.txtMuscleCible.setText(exercice.getMusclecible());



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
        ImageView imageView;  // Add ImageView for the image

        public ExerciceViewHolder(@NonNull View itemView, final MyExerciceAdapterForUser.OnItemClickListener listener) {
            super(itemView);
            txtNomExercice = itemView.findViewById(R.id.recnameEx);
          //  txtMuscleCible = itemView.findViewById(R.id.reczonecible);


            imageView = itemView.findViewById(R.id.recImageEx);  // Initialize ImageView


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
