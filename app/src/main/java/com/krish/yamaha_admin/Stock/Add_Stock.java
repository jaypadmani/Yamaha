package com.krish.yamaha_admin.Stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.krish.yamaha_admin.Bike.BikeData;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.Add_Detail;

public class Add_Stock extends AppCompatActivity {

    private EditText addSTName,addSTQuntity,addSTPrice,addSTDate;
    private Spinner addSTCategory;
    Button addStockBtn;

    private String name,quntity,price,date;

    private final int REQ = 1;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private String category;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    String DownloadUrl = "";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        addSTName = findViewById(R.id.addSTName);
        addSTQuntity = findViewById(R.id.addSTQuntity);
        addSTPrice = findViewById(R.id.addSTPrice);
        addSTDate = findViewById(R.id.addSTDate);
        addSTCategory = findViewById(R.id.addSTCategory);
        addStockBtn = findViewById(R.id.addStockBtn);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Stock.this, Stock_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Add Stock");


        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("stock");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","MOTORCYCLES","SCOOTERS","ACCESSORIES"};
        addSTCategory.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        addSTCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addSTCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        name = addSTName.getText().toString();
        quntity = addSTQuntity.getText().toString();
        price = addSTPrice.getText().toString();
        date = addSTDate.getText().toString();

        if (name.isEmpty()){
            addSTName.setError("Empty");
            addSTName.requestFocus();
        }else if (quntity.isEmpty()){
            addSTQuntity.setError("Empty");
            addSTQuntity.requestFocus();
        }else if (price.isEmpty()){
            addSTPrice.setError("Empty");
            addSTPrice.requestFocus();
        }else if (date.isEmpty()) {
            addSTDate.setError("Empty");
            addSTDate.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(Add_Stock.this, "please provide category", Toast.LENGTH_SHORT).show();
        }else {
            pd.setMessage("Uploading...");
            pd.show();
            insertData();
        }
    }
    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        StockData StockData = new StockData(name,quntity,price,date,uniqueKey);

        dbRef.child(uniqueKey).setValue(StockData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Stock.this,"User Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Stock.this,"Something Went Worng",Toast.LENGTH_SHORT).show();
            }
        });
    }
}