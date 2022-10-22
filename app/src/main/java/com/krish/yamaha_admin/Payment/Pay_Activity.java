package com.krish.yamaha_admin.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.R;

public class Pay_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pay_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Parts ! Accessories");
    }
}