package com.example.carsaleproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsaleproject.R;
import com.example.carsaleproject.model.Advertise;

import java.util.ArrayList;

public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.AdvertiseHolder> {
    private ArrayList<Advertise> advertiseArrayList;
    private Context context;

    class AdvertiseHolder extends RecyclerView.ViewHolder{
        //ImageView car_picture;
        TextView title,car_brand,car_price;
        public AdvertiseHolder(@NonNull View itemView) {
            super(itemView);
            //car_picture = itemView.findViewById(R.id.recyclerView_car_picture);
            title = itemView.findViewById(R.id.recyclerView_txt_title);
            car_brand = itemView.findViewById(R.id.recyclerView_txt_car_brand);
            car_price = itemView.findViewById(R.id.recyclerView_txt_car_price);
        }
    }

    public AdvertiseAdapter(ArrayList<Advertise> advertiseArrayList, Context context) {
        this.advertiseArrayList = advertiseArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdvertiseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row,parent,false);
        return new AdvertiseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertiseHolder holder, int position) {
        holder.title.setText(advertiseArrayList.get(position).title);
        holder.car_brand.setText(advertiseArrayList.get(position).carBrand);
        holder.car_price.setText(advertiseArrayList.get(position).carPrice);
    }

    @Override
    public int getItemCount() {
        return advertiseArrayList.size();
    }


}
