package com.krish.yamaha_admin.Bike;

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
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.UserAdapter;
import com.krish.yamaha_admin.User.UserData;
import com.krish.yamaha_admin.User.User_Activity;

import java.util.ArrayList;

public class Bike_Activity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView bikeDetail,ScooterDetail;
    private LinearLayout bikeNoData,scooterNoData;
    private ArrayList<BikeData> list1,list2;
    private DatabaseReference reference,dbRef;
    private BikeAdapter adapter;

    private ImageView back_icon;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        fab = findViewById(R.id.fab1);
        bikeDetail = findViewById(R.id.bikeDetail);
        ScooterDetail = findViewById(R.id.ScooterDetail);

        bikeNoData = findViewById(R.id.bikeNoData);
        scooterNoData = findViewById(R.id.scooterNoData);

        back_icon = findViewById(R.id.back_icon);
        header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bike_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Bike Information");

        reference = FirebaseDatabase.getInstance().getReference().child("bike");

        bike_Detail();
        scooterDeetail();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bike_Activity.this, Add_Bike.class);
                startActivity(intent);
            }
        });

    }
    private void bike_Detail() {
        dbRef = reference.child("MOTORCYCLES");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<BikeData>();
                if (!snapshot.exists()){
                    bikeNoData.setVisibility(View.VISIBLE);
                    bikeDetail.setVisibility(View.GONE);
                }else {
                    bikeNoData.setVisibility(View.GONE);
                    bikeDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        BikeData data = snapshot1.getValue(BikeData.class);
                        list1.add(data);
                    }
                    bikeDetail.setHasFixedSize(true);
                    bikeDetail.setLayoutManager(new LinearLayoutManager(Bike_Activity.this));
                    adapter = new BikeAdapter(list1,Bike_Activity.this,"MOTORCYCLES");
                    bikeDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Bike_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scooterDeetail() {
        dbRef = reference.child("SCOOTERS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<BikeData>();
                if (!snapshot.exists()){
                    scooterNoData.setVisibility(View.VISIBLE);
                    ScooterDetail.setVisibility(View.GONE);
                }else {
                    scooterNoData.setVisibility(View.GONE);
                    ScooterDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        BikeData data = snapshot1.getValue(BikeData.class);
                        list2.add(data);
                    }
                    ScooterDetail.setHasFixedSize(true);
                    ScooterDetail.setLayoutManager(new LinearLayoutManager(Bike_Activity.this));
                    adapter = new BikeAdapter(list2,Bike_Activity.this,"SCOOTERS");
                    ScooterDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Bike_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}