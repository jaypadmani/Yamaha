package com.krish.yamaha_admin.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.UserData;
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
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.Stock.Add_Stock;
import com.krish.yamaha_admin.Stock.Stock_Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Detail extends AppCompatActivity {

    private ImageView addImage;
    private EditText addName,addEmail,addPassword,addBikemodel;
    private Spinner addCategory;
    Button addUserBtn;

    private String name,email,password,bikemodel,downloadUrl = "";

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
        setContentView(R.layout.activity_add_detail);
        addImage = findViewById(R.id.addImage);
        addName = findViewById(R.id.addName);
        addEmail = findViewById(R.id.addEmail);
        addPassword = findViewById(R.id.addPassword);
        addBikemodel = findViewById(R.id.addBikemodel);
        addCategory = findViewById(R.id.addCategory);
        addUserBtn = findViewById(R.id.addUserBtn);

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Detail.this, Stock_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Add Detail");


        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("user");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","MOTORCYCLES","SCOOTERS"};
        addCategory.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        addCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addCategory .getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        name = addName.getText().toString();
        email = addEmail.getText().toString();
        password = addPassword.getText().toString();
        bikemodel = addBikemodel.getText().toString();

        if (name.isEmpty()){
            addName.setError("Empty");
            addName.requestFocus();
        }else if (email.isEmpty()){
            addEmail.setError("Empty");
            addEmail.requestFocus();
        }else if (password.isEmpty()){
            addPassword.setError("Empty");
            addPassword.requestFocus();
        }else if (bikemodel.isEmpty()) {
            addBikemodel.setError("Empty");
            addBikemodel.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(Add_Detail.this, "please provide category", Toast.LENGTH_SHORT).show();
        }else if (bitmap == null){
            pd.setMessage("Uploading...");
            pd.show();
            registerUser();
            insertData();
        }else {
            pd.setMessage("Uploading...");
            pd.show();
            registerUser();
            insertImage();
        }
    }


    private void insertImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("User ").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Add_Detail.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(Add_Detail.this,"Somthing went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void registerUser() {
        auth.createUserWithEmailAndPassword(addEmail.getText().toString(),addPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Add_Detail.this, "User Successful", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Add_Detail.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        com.krish.yamaha_admin.User.UserData UserData = new com.krish.yamaha_admin.User.UserData(name,email,password,bikemodel,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(UserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Add_Detail.this,"User Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Detail.this,"Something Went Worng",Toast.LENGTH_SHORT).show();
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
            addImage.setImageBitmap(bitmap);
        }
    }

}