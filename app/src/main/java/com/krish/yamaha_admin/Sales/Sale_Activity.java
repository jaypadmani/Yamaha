package com.krish.yamaha_admin.Sales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.Parts.Add_Parts;
import com.krish.yamaha_admin.Parts.Aerox_Activity;
import com.krish.yamaha_admin.Parts.PartsAdapter;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.R;

import java.util.ArrayList;

public class Sale_Activity extends AppCompatActivity {

    FloatingActionButton fab3;
    private RecyclerView saleDetail;
    private LinearLayout saleNoData;
    private ArrayList<SaleData> list1;
    private DatabaseReference reference,dbRef;
    private SaleAdapter adapter;
    private ImageView back_icon;
    private TextView header_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
//        name,mobile_number,price,aadhar_card-no,paym..type,photo;

        fab3 = findViewById(R.id.fab3);
        saleDetail = findViewById(R.id.saleDetail);

        saleNoData = findViewById(R.id.saleNoData);
        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sale_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Sale Activity");



        reference = FirebaseDatabase.getInstance().getReference().child("sale");

        Sale_Detail();

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sale_Activity.this, Add_Sales.class);
                startActivity(intent);
            }
        });

    }

    private void Sale_Detail() {
        dbRef = reference.child("All Payment Done");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<SaleData>();
                if (!snapshot.exists()){
                    saleNoData.setVisibility(View.VISIBLE);
                    saleDetail.setVisibility(View.GONE);
                }else {
                    saleNoData.setVisibility(View.GONE);
                    saleDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        SaleData data = snapshot1.getValue(SaleData.class);
                        list1.add(data);
                    }
                    saleDetail.setHasFixedSize(true);
                    saleDetail.setLayoutManager(new LinearLayoutManager(Sale_Activity.this));
                    adapter = new SaleAdapter(list1,Sale_Activity.this,"All Payment Done");
                    saleDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Sale_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}