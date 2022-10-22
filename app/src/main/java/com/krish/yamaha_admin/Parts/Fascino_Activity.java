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
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.R;

import java.util.ArrayList;

public class Fascino_Activity extends AppCompatActivity {
    FloatingActionButton fabF;
    private RecyclerView fascinoDetail;
    private LinearLayout fascinoNoData;
    private ArrayList<PartsData> list1;
    private DatabaseReference reference,dbRef;
    private PartsAdapter adapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fascino);

        fabF = findViewById(R.id.fabF);
        fascinoDetail = findViewById(R.id.fascinoDetail);

        fascinoNoData = findViewById(R.id.fascinoNoData);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fascino_Activity.this, Parts_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Facino 125");



        reference = FirebaseDatabase.getInstance().getReference().child("parts");

        fascino_Detail();


        fabF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fascino_Activity.this, Add_Parts.class);
                startActivity(intent);
            }
        });

    }

    private void fascino_Detail() {
        dbRef = reference.child("Facino125");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<PartsData>();
                if (!snapshot.exists()){
                    fascinoNoData.setVisibility(View.VISIBLE);
                    fascinoDetail.setVisibility(View.GONE);
                }else {
                    fascinoNoData.setVisibility(View.GONE);
                    fascinoDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        PartsData data = snapshot1.getValue(PartsData.class);
                        list1.add(data);
                    }
                    fascinoDetail.setHasFixedSize(false);
                    gridLayoutManager = new GridLayoutManager(Fascino_Activity.this,2);
                    fascinoDetail.setLayoutManager(gridLayoutManager);
                    fascinoDetail.setLayoutManager(new LinearLayoutManager(Fascino_Activity.this));
                    adapter = new PartsAdapter(list1,Fascino_Activity.this,"Aerox155");
                    fascinoDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Fascino_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}