package com.example.assignment6.ui.recyclerview.viewholder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Locale;


public abstract class ShoppingViewHolderPlus<A> extends RecyclerView.ViewHolder {
    public static final String HASH_STRING_TITLE = "mTitle";
    public static final String HASH_STRING_COST = "mCost";
//    public static final String HASH_STRING_TITLE = "mTitle";

    public final View mView;
    public TextView mTitle;
    public TextView mCost;
    public ImageView mImage;

    public A mItem;

    public ShoppingViewHolderPlus(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public abstract long getID();

    public void setCostString(double cost) {
        mCost.setText(
                String.format(
                        Locale.US,
                        "$%s",
                        new DecimalFormat("#.##").format(cost)
                )
        );
    }

    public void setTitleString(String title) {
        mTitle.setText(title);
    }

    public void setImage(Bitmap bitmap) {
        mImage.setImageBitmap(bitmap);
    }
}
