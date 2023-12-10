package com.example.my_fit_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//
//    private List<Category> dataList;
//    private Context context;
//
//    public MyAdapter(Context context, List<Category> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
//        return new MyViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);
//        holder.recyclerCaption.setText(dataList.get(position).getCategName());
//    }
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView recyclerImage;
//        TextView recyclerCaption;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            recyclerImage = itemView.findViewById(R.id.recImage);
//            recyclerCaption = itemView.findViewById(R.id.recTitle);
//        }
//    }
//}
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Category> dataList;
    private Context context;
    private OnItemClickListener mListener;

    public MyAdapter(Context context, List<Category> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Define an interface to handle button clicks
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onEditClick(int position);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);
        holder.recyclerCaption.setText(dataList.get(position).getCategName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // MyViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption;
        ImageButton btnDelete;
        ImageButton btnEdit;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recImage);
            recyclerCaption = itemView.findViewById(R.id.recTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            // Handle button click
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            // Handle button click
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            // Handle item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
