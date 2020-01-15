package com.example.assignment6.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.R;
import com.example.assignment6.data.room_items.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mName;

    public Category mItem;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mName = mView.findViewById(R.id.spinner_text_name);
    }
}
