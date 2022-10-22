package com.krish.yamaha_admin.Sales;

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

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleViewAdapter>{
    private List<SaleData> list;
    private Context context;
    private String category;

    public SaleAdapter(List<SaleData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public SaleAdapter.SaleViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sale_layout,parent,false);
        return new SaleAdapter.SaleViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleAdapter.SaleViewAdapter holder, int position) {
        final SaleData item = list.get(position);
        holder.name.setText(item.getName());
        holder.number.setText(item.getNumber());
        holder.price.setText(item.getPrice());
        holder.aadharnumber.setText(item.getAadharnumber());
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

    public class SaleViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,number,price,aadharnumber;
        private ImageView imageView;
        public SaleViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.saleName);
            number = itemView.findViewById(R.id.salenumber);
            price = itemView.findViewById(R.id.salePrice);
            aadharnumber = itemView.findViewById(R.id.saleaadhar);
            imageView = itemView.findViewById(R.id.saleImage);
        }
    }
}
