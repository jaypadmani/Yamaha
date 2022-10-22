package com.krish.yamaha_admin.User;

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

import java.util.ArrayList;
import java.util.List;

public class User_Activity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView mcDetail,scDetail;
    private LinearLayout mcNoData,scNoData;
    private List<UserData> list1,list2;
    private DatabaseReference reference,dbRef;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fab = findViewById(R.id.fab);
        mcDetail = findViewById(R.id.mcDetail);
        scDetail = findViewById(R.id.scDetail);

        mcNoData = findViewById(R.id.mcNoData);
        scNoData = findViewById(R.id.scNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("user");

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("User Detail");


        mcDetail();
        scDetail();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Activity.this,Add_Detail.class);
                startActivity(intent);
            }
        });
    }
    private void mcDetail() {
        dbRef = reference.child("MOTORCYCLES");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<UserData>();
                if (!snapshot.exists()){
                    mcNoData.setVisibility(View.VISIBLE);
                    mcDetail.setVisibility(View.GONE);
                }else {
                    mcNoData.setVisibility(View.GONE);
                    mcDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        UserData data = snapshot1.getValue(UserData.class);
                        list1.add(data);
                    }
                    mcDetail.setHasFixedSize(true);
                    mcDetail.setLayoutManager(new LinearLayoutManager(User_Activity.this));
                    adapter = new UserAdapter(list1,User_Activity.this,"MOTORCYCLES");
                    mcDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void scDetail() {
        dbRef = reference.child("SCOOTERS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<UserData>();
                if (!snapshot.exists()){
                    scNoData.setVisibility(View.VISIBLE);
                    scDetail.setVisibility(View.GONE);
                }else {
                    scNoData.setVisibility(View.GONE);
                    scDetail.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        UserData data = snapshot1.getValue(UserData.class);
                        list2.add(data);
                    }
                    scDetail.setHasFixedSize(true);
                    scDetail.setLayoutManager(new LinearLayoutManager(User_Activity.this));
                    adapter = new UserAdapter(list2,User_Activity.this,"SCOOTERS");
                    scDetail.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Activity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}