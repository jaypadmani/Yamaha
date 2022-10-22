package com.krish.yamaha_admin.Bike;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.Add_Detail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Bike extends AppCompatActivity {

    private ImageView addBikeImage;
    private EditText addBikeName,addBikeDetail,addBikePrice;
    private Spinner addBikeCategory;
    Button addBikeBtn;

    private String name,detail,price,downloadUrl = "";

    private final int REQ = 1;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private String category;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    String DownloadUrl = "";

    private ImageView back_icon;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        addBikeImage = findViewById(R.id.addBikeImage);
        addBikeName = findViewById(R.id.addBikeName);
        addBikeDetail = findViewById(R.id.addBikeDetail);
        addBikePrice = findViewById(R.id.addBikePrice);
        addBikeCategory = findViewById(R.id.addBikeCategory);
        addBikeBtn = findViewById(R.id.addBikeBtn);

        back_icon = findViewById(R.id.back_icon);
        header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Bike.this, Bike_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Add Bike");


        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("bike");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","MOTORCYCLES","SCOOTERS"};
        addBikeCategory.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        addBikeCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addBikeCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addBikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addBikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        name = addBikeName.getText().toString();
        detail = addBikeDetail.getText().toString();
        price = addBikePrice.getText().toString();

        if (name.isEmpty()){
            addBikeName.setError("Empty");
            addBikeName.requestFocus();
        }else if (detail.isEmpty()){
            addBikeDetail.setError("Empty");
            addBikeDetail.requestFocus();
        }else if (price.isEmpty()){
            addBikePrice.setError("Empty");
            addBikePrice.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(Add_Bike.this, "please provide category", Toast.LENGTH_SHORT).show();
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
        filePath = storageReference.child("Bike ").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Add_Bike.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(Add_Bike.this,"Somthing went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        BikeData BikeData = new BikeData(name,detail,price,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(BikeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Bike.this,"User Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Bike.this,"Something Went Worng",Toast.LENGTH_SHORT).show();
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
            addBikeImage.setImageBitmap(bitmap);
        }
    }
}