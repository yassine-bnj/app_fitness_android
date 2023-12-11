package com.example.my_fit_app;

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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {


    private List<Category> dataList;
    private Context context;
    private CategoriesAdapter.OnItemClickListener mListener;

    public CategoriesAdapter(Context context, List<Category> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Define an interface to handle button clicks
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onEditClick(int position);
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategoriesAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_user, parent, false);
        return new CategoriesAdapter.MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.MyViewHolder holder, int position) {
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


        public MyViewHolder(@NonNull View itemView, final CategoriesAdapter.OnItemClickListener listener) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recImage);
            recyclerCaption = itemView.findViewById(R.id.recTitle);

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
