package com.krish.yamaha_admin.Bike;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.Update_User;
import com.krish.yamaha_admin.User.UserAdapter;
import com.krish.yamaha_admin.User.UserData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.BikeViewAdapter>{

    private List<BikeData> list;
    private Context context;
    private String category;

    public BikeAdapter(List<BikeData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public BikeAdapter.BikeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bike_layout,parent,false);
        return new BikeAdapter.BikeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeAdapter.BikeViewAdapter holder, int position) {
        final BikeData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getDetail());
        holder.price.setText(item.getPrice());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BikeViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,price;
        private ImageView imageView;
        public BikeViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bikename);
            email = itemView.findViewById(R.id.bikedetail);
            price = itemView.findViewById(R.id.bikeprice);
            imageView = itemView.findViewById(R.id.bikeimage);
        }
    }
}
