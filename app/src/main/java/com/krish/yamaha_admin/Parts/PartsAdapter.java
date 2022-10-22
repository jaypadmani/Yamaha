package com.krish.yamaha_admin.Parts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krish.yamaha_admin.Bike.BikeAdapter;
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.PartsViewAdapter>{

    private List<PartsData> list;
    private Context context;
    private String category;

    public PartsAdapter(List<PartsData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public PartsAdapter.PartsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parts_layout,parent,false);
        return new PartsAdapter.PartsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartsAdapter.PartsViewAdapter holder, int position) {
        final PartsData item = list.get(position);
        holder.name.setText(item.getName());
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

    public class PartsViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,price;
        private ImageView imageView;
        public PartsViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.partsname);
            price = itemView.findViewById(R.id.partsprice);
            imageView = itemView.findViewById(R.id.partsimage);
        }
    }
}
