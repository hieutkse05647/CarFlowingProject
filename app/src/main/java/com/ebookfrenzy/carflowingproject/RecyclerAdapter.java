package com.ebookfrenzy.carflowingproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebookfrenzy.carflowingproject.DAO.FireStoreDAO;
import com.ebookfrenzy.carflowingproject.Model.Car;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private int[] images = { R.drawable.huyndai_accent_20182,
            R.drawable.posche911,
            R.drawable.vinfast2019,
            R.drawable.huyndai_accent_20182,
            R.drawable.posche911,
            R.drawable.kia_morning,
            R.drawable.huyndai_accent_20182,
            R.drawable.posche911 };

    public RecyclerAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Car car = FireStoreDAO.getInstance().getListCars().get(i);
        viewHolder.itemTitle.setText(car.getName());
        viewHolder.itemDetail.setText(car.getCarPlate());
        viewHolder.itemImage.setImageResource(images[i]);
        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GPSActivity.class);
                intent.putExtra("CAR_PLATE", car.getCarPlate());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FireStoreDAO.getInstance().getListCars().size();
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