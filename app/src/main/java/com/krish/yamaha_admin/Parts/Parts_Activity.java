package com.krish.yamaha_admin.Parts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.R;

public class Parts_Activity extends AppCompatActivity {

    private CardView aerox,fascino,fz_x,fzs_25,fzs_fi,mt_15,r15v4,rayzr_125,yzf_r15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);

        aerox = findViewById(R.id.aerox);
        fascino = findViewById(R.id.fascino);
        fz_x = findViewById(R.id.fz_x);
        fzs_25 = findViewById(R.id.fzs_25);
        fzs_fi = findViewById(R.id.fzs_fi);
        mt_15 = findViewById(R.id.mt_15);
        r15v4 = findViewById(R.id.r15v4);
        rayzr_125 = findViewById(R.id.rayzr_125);
        yzf_r15 = findViewById(R.id.yzf_r15);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Parts ! Accessories");

        aerox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,Aerox_Activity.class);
                startActivity(intent);
            }
        });
        fascino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,Fascino_Activity.class);
                startActivity(intent);
            }
        });
        fz_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,Fzx_Activity.class);
                startActivity(intent);
            }
        });
        fzs_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,fzs25_Activity.class);
                startActivity(intent);
            }
        });
        fzs_fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,Fzsfi_Activity.class);
                startActivity(intent);
            }
        });
        mt_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,mt15_Activity.class);
                startActivity(intent);
            }
        });
        r15v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,R15v4_Activity.class);
                startActivity(intent);
            }
        });
        rayzr_125.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,rayzr125_Activity.class);
                startActivity(intent);
            }
        });
        yzf_r15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parts_Activity.this,yzfr15_Activity.class);
                startActivity(intent);
            }
        });
    }
}