package com.krish.yamaha_admin.Stock;

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

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewAdapter>{

    private List<StockData> list;
    private Context context;
    private String category;

    public StockAdapter(List<StockData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public StockViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_layout,parent,false);
        return new StockAdapter.StockViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.StockViewAdapter holder, int position) {
        final StockData item = list.get(position);
        holder.name.setText("Stock Name: "+item.getName());
        holder.quntity.setText("Stock Quntity: "+item.getQuntity());
        holder.price.setText("Stock Price: "+item.getPrice());
        holder.date.setText("Stock Date: "+item.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StockViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,quntity,price,date;
        public StockViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stockName);
            quntity = itemView.findViewById(R.id.stockQuntity);
            price = itemView.findViewById(R.id.StockPrice);
            date = itemView.findViewById(R.id.stockDate);
        }
    }
}
