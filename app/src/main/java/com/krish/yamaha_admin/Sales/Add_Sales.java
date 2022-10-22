package com.krish.yamaha_admin.Sales;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.Parts.Add_Parts;
import com.krish.yamaha_admin.Parts.Parts_Activity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.Add_Detail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Sales extends AppCompatActivity {

    private ImageView addSImage;
    private EditText addSName,addSNumber,addSPrice,addSAadhar;
    private Spinner addCategory;
    Button addSaleBtn;

    private String name,number,price,aadharnumber,downloadUrl = "";

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
        setContentView(R.layout.activity_add_sales);
        addSImage = findViewById(R.id.addSImage);
        addSName = findViewById(R.id.addSName);
        addSNumber = findViewById(R.id.addSNumber);
        addSPrice = findViewById(R.id.addSPrice);
        addSAadhar = findViewById(R.id.addSAadhar);
        addCategory = findViewById(R.id.addSCategory);
        addSaleBtn = findViewById(R.id.addSaleBtn);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Sales.this, Sale_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Add Sales");


        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("sale");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","All Payment Done","EMI"};
        addCategory.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        addCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        name = addSName.getText().toString();
        number = addSNumber.getText().toString();
        price = addSPrice.getText().toString();
        aadharnumber = addSAadhar.getText().toString();

        if (name.isEmpty()){
            addSName.setError("Empty");
            addSName.requestFocus();
        }else if (number.isEmpty()){
            addSNumber.setError("Empty");
            addSNumber.requestFocus();
        }else if (price.isEmpty()){
            addSPrice.setError("Empty");
            addSPrice.requestFocus();
        }else if (aadharnumber.isEmpty()) {
            addSAadhar.setError("Empty");
            addSAadhar.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(Add_Sales.this, "please provide category", Toast.LENGTH_SHORT).show();
        }else if (bitmap == null){
            pd.setMessage("Uploading...");
            pd.show();
            insertData();
        }else {
            pd.setMessage("Uploading...");
            pd.show();
            insertImage();
        }
    }


    private void insertImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Sale ").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Add_Sales.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DownloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(Add_Sales.this,"Somthing went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        SaleData SaleData = new SaleData(name,number,price,aadharnumber,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(SaleData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Sales.this,"User Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Sales.this,"Something Went Worng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ &&  resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addSImage.setImageBitmap(bitmap);
        }
    }


}