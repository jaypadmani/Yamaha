package com.krish.yamaha_admin.Parts;

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
import com.krish.yamaha_admin.Bike.Add_Bike;
import com.krish.yamaha_admin.Bike.BikeAdapter;
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.Bike.Bike_Activity;
import com.krish.yamaha_admin.R;

import java.util.ArrayList;

public class Aerox_Activity extends AppCompatActivity {

    FloatingActionButton fab2;
    private RecyclerView aeroxDetail;
    private LinearLayout aeroxNoData;
    private ArrayList<PartsData> list1;
    private DatabaseReference reference,dbRef;
    private PartsAdapter adapter;
    GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aerox);

        fab2 = findViewById(R.id.fab2);
        aeroxDetail = findViewById(R.id.aeroxDetail);

        aeroxNoData = findViewById(R.id.aeroxNoData);
        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aerox_Activity.this, Parts_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Aerox 155");


        reference = FirebaseDatabase.getInstance().getReference().child("parts");

        aerox_Detail();


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aerox_Activity.this, Add_Parts.class);
                startActivity(intent);
            }
        });

    }

    private void aerox_Detail() {
        dbRef = reference.child("Aerox155");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<PartsData>();
                if (!snapshot.exists()){
                    aeroxNoData.setVisibility(View.VISIBLE);
                    aeroxDetail.setVisibility(View.GONE);
                }else {
                    aeroxNoData.setVisibility(View.GONE);
                    aeroxDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        PartsData data = snapshot1.getValue(PartsData.class);
                        list1.add(data);
                    }
                    aeroxDetail.setHasFixedSize(false);
                    gridLayoutManager = new GridLayoutManager(Aerox_Activity.this,2);
                    aeroxDetail.setLayoutManager(gridLayoutManager);
                    aeroxDetail.setLayoutManager(new LinearLayoutManager(Aerox_Activity.this));
                    adapter = new PartsAdapter(list1,Aerox_Activity.this,"Aerox155");
                    aeroxDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Aerox_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}