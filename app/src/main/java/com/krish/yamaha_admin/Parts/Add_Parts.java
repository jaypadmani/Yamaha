package com.krish.yamaha_admin.Parts;

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
import com.krish.yamaha_admin.Bike.Add_Bike;
import com.krish.yamaha_admin.Bike.Bike_Activity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.User.Add_Detail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Parts extends AppCompatActivity {

    private ImageView addAImage;
    private EditText addAName,addAPrice;
    private Spinner addParts;
    Button addPartsBtn;

    private String name,price,downloadUrl = "";

    private final int REQ = 1;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private String category;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    String DownloadUrl = "";
    private FirebaseAuth auth;

    private ImageView back_icon;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parts);
        addAImage = findViewById(R.id.addAImage);
        addAName = findViewById(R.id.addAName);
        addAPrice = findViewById(R.id.addAPrice);
        addParts = findViewById(R.id.addParts);
        addPartsBtn = findViewById(R.id.addPartsBtn);

        back_icon = findViewById(R.id.back_icon);
        header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Parts.this, Parts_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Add Parts");


        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("parts");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","Aerox155","Facino125","FZ-X","FZS-25","FZS-FI","MT-15","R15V4","RAYZR125","YZF-R15"};
        addParts.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        addParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addParts.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addAImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addPartsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        name = addAName.getText().toString();
        price = addAPrice.getText().toString();

        if (name.isEmpty()){
            addAName.setError("Empty");
            addAName.requestFocus();
        }else if (price.isEmpty()){
            addAPrice.setError("Empty");
            addAPrice.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(Add_Parts.this, "please provide category", Toast.LENGTH_SHORT).show();
        }else if (bitmap == null){
            pd.setMessage("Please Upload Image...");
            pd.show();
        }else {
            pd.setMessage("Uploading...");
            pd.show();
            insertImage();
            insertData();
        }
    }

    private void insertImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Parts ").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Add_Parts.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(Add_Parts.this,"Somthing went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        PartsData PartsData = new PartsData(name,price,DownloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(PartsData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Parts.this,"Parts Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Parts.this,"Something Went Worng",Toast.LENGTH_SHORT).show();
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
            addAImage.setImageBitmap(bitmap);
        }
    }
}