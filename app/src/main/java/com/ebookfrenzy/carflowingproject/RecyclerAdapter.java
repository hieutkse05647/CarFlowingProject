package com.ebookfrenzy.carflowingproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] titles = {"Huyndai Accent ","Posche Mancan","Vin Fast ","Huyndai Accent ","Posche Mancan", "Kia Morning","Huyndai Accent ","Posche Mancan"};

    private String[] details = {"Huyndai Accent 2018",
            "Posche Mancan", "Vin Fast 2019",
            "Car 4 details", "Car 5 details",
            "Car 6 details", "Car 7 details",
            "Car 8 details"};

    private int[] images = { R.drawable.huyndai_accent_20182,
            R.drawable.posche911,
            R.drawable.vinfast2019,
            R.drawable.huyndai_accent_20182,
            R.drawable.posche911,
            R.drawable.kia_morning,
            R.drawable.huyndai_accent_20182,
            R.drawable.posche911 };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail= (TextView)itemView.findViewById(R.id.item_detail);
        }
    }

}