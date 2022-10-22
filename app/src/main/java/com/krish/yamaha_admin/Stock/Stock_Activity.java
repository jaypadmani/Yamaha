package com.krish.yamaha_admin.Stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krish.yamaha_admin.Bike.Add_Bike;
import com.krish.yamaha_admin.Bike.BikeAdapter;
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.Bike.Bike_Activity;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.R;

import java.util.ArrayList;

public class Stock_Activity extends AppCompatActivity {

    FloatingActionButton fabS;
    private RecyclerView bikeSDetail,scooterSDetail,accessorySDetail;
    private LinearLayout bikeSNoData,scooterSNoData,accessorySNoData;
    private ArrayList<StockData> list1,list2,list3;
    private DatabaseReference reference,dbRef;
    private StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        fabS = findViewById(R.id.fabS);
        bikeSDetail = findViewById(R.id.bikeSDetail);
        scooterSDetail = findViewById(R.id.scooterSDetail);
        accessorySDetail = findViewById(R.id.accessorySDetail);

        bikeSNoData = findViewById(R.id.bikeSNoData);
        scooterSNoData = findViewById(R.id.scooterSNoData);
        accessorySNoData = findViewById(R.id.accessorySNoData);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stock_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Stock Activity");

        reference = FirebaseDatabase.getInstance().getReference().child("stock");

        bike_Detail();
        scooterDetail();
        accessoryDetail();

        fabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stock_Activity.this, Add_Stock.class);
                startActivity(intent);
            }
        });
    }
    private void bike_Detail() {
        dbRef = reference.child("MOTORCYCLES");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<StockData>();
                if (!snapshot.exists()){
                    bikeSNoData.setVisibility(View.VISIBLE);
                    bikeSDetail.setVisibility(View.GONE);
                }else {
                    bikeSNoData.setVisibility(View.GONE);
                    bikeSDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        StockData data = snapshot1.getValue(StockData.class);
                        list1.add(data);
                    }
                    bikeSDetail.setHasFixedSize(true);
                    bikeSDetail.setLayoutManager(new LinearLayoutManager(Stock_Activity.this));
                    adapter = new StockAdapter(list1,Stock_Activity.this,"MOTORCYCLES");
                    bikeSDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Stock_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scooterDetail() {
        dbRef = reference.child("SCOOTERS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<StockData>();
                if (!snapshot.exists()){
                    scooterSNoData.setVisibility(View.VISIBLE);
                    scooterSDetail.setVisibility(View.GONE);
                }else {
                    scooterSNoData.setVisibility(View.GONE);
                    scooterSDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        StockData data = snapshot1.getValue(StockData.class);
                        list2.add(data);
                    }
                    scooterSDetail.setHasFixedSize(true);
                    scooterSDetail.setLayoutManager(new LinearLayoutManager(Stock_Activity.this));
                    adapter = new StockAdapter(list2,Stock_Activity.this,"SCOOTERS");
                    scooterSDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Stock_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void accessoryDetail() {
        dbRef = reference.child("ACCESSORIES");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<StockData>();
                if (!snapshot.exists()){
                    accessorySNoData.setVisibility(View.VISIBLE);
                    accessorySDetail.setVisibility(View.GONE);
                }else {
                    accessorySNoData.setVisibility(View.GONE);
                    accessorySDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        StockData data = snapshot1.getValue(StockData.class);
                        list3.add(data);
                    }
                    accessorySDetail.setHasFixedSize(true);
                    accessorySDetail.setLayoutManager(new LinearLayoutManager(Stock_Activity.this));
                    adapter = new StockAdapter(list3,Stock_Activity.this,"ACCESSORIES");
                    accessorySDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Stock_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}