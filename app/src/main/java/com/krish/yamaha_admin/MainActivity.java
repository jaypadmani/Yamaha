package com.krish.yamaha_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.krish.yamaha_admin.Bike.Bike_Activity;
import com.krish.yamaha_admin.Login.Login_Activity;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.Payment.Pay_Activity;
import com.krish.yamaha_admin.Sales.Sale_Activity;
import com.krish.yamaha_admin.Stock.Stock_Activity;
import com.krish.yamaha_admin.User.User_Activity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout navDrawer;
    private NavigationView navView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private CardView vehicles,accessories,stockhome,salehome;
    private View header;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navDrawer = findViewById(R.id.navDrawer);
        navView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        vehicles = findViewById(R.id.vehicles);
        accessories = findViewById(R.id.accessories);
        stockhome = findViewById(R.id.stockhome);
        salehome = findViewById(R.id.salehome);

        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Bike_Activity.class);
                startActivity(intent1);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Parts_Activity.class);
                startActivity(intent1);
            }
        });
        stockhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Stock_Activity.class);
                startActivity(intent1);
            }
        });
        salehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Sale_Activity.class);
                startActivity(intent1);
            }
        });



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle=new ActionBarDrawerToggle(this,navDrawer,toolbar,R.string.Start,R.string.Close);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.bringToFront();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.stock:
                        Intent intent1 = new Intent(MainActivity.this, Stock_Activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.sale:
                        Intent intent2 = new Intent(MainActivity.this, Sale_Activity.class);
                        startActivity(intent2);
                        break;
                    case R.id.user:
                        Intent intent3 = new Intent(MainActivity.this, User_Activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.bikedetail:
                        Intent intent4 = new Intent(MainActivity.this, Bike_Activity.class);
                        startActivity(intent4);
                        break;
                    case R.id.pay:
                        Intent intent5 = new Intent(MainActivity.this, Pay_Activity.class);
                        startActivity(intent5);
                        break;
                    case R.id.parts:
                        Intent intent6 = new Intent(MainActivity.this, Parts_Activity.class);
                        startActivity(intent6);
                        break;
                    case R.id.help:
                        Intent intent7 = new Intent(Intent.ACTION_SEND);
                        intent7.putExtra(Intent.EXTRA_EMAIL,new String[]{"20ce089@charusat.edu.in"});
                        intent7.setType("text/plain");
                        intent7.setPackage("com.google.android.gmail");
                        startActivity(intent7);
                        break;

                }
                return true;
            }
        });
        header = navView.getHeaderView(0);
        if (user!=null){

            TextView userEmail = (TextView) header.findViewById(R.id.userEmail);
            userEmail.setText(user.getEmail());

            Button logout = (Button) header.findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                    finish();
                }
            });

        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (user==null){
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
            finish();
        }
    }
}