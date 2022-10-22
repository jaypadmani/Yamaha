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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.R;
import com.krish.yamaha_admin.Sales.Sale_Activity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Update_User extends AppCompatActivity {
    private ImageView updateUserImage;
    private EditText updateUserName,updateUserEmail,updateUserPassword,updateUserBike;
    private Button updateUserbtn,deleteUserbtn;

    private String name,email,image,password,bike;
    private ProgressDialog pd;

    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    String DownloadUrl,category,uniqueKey;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        bike = getIntent().getStringExtra("bike");
        image = getIntent().getStringExtra("image");

        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");

        ImageView back_icon = findViewById(R.id.back_icon);
        TextView header_text = findViewById(R.id.header_text);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_User.this, User_Activity.class);
                startActivity(intent);
            }
        });
        header_text.setText("Update Activity");


        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("user");
        storageReference = FirebaseStorage.getInstance().getReference();

        updateUserImage = findViewById(R.id.updateUserImage);
        updateUserName = findViewById(R.id.updateUserName);
        updateUserEmail = findViewById(R.id.updateUserEmail);
        updateUserPassword = findViewById(R.id.updateUserPassword);
        updateUserBike = findViewById(R.id.updateUserBike);
        updateUserbtn = findViewById(R.id.updateUserbtn);
        deleteUserbtn = findViewById(R.id.deleteUserbtn);

        try {
            Picasso.get().load(image).into(updateUserImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUserEmail.setText(email);
        updateUserName.setText(name);
        updateUserPassword.setText(password);
        updateUserBike.setText(bike);

        updateUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        updateUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = updateUserName.getText().toString();
                email = updateUserEmail.getText().toString();
                password = updateUserPassword.getText().toString();
                bike = updateUserBike.getText().toString();
                checkValidation();
            }
        });
        deleteUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Update_User.this, "User Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Update_User.this,User_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update_User.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        if(name.isEmpty()){
            updateUserName.setError("Empty");
            updateUserName.requestFocus();
        }else if(email.isEmpty()){
            updateUserEmail.setError("Empty");
            updateUserEmail.requestFocus();
        }else if(password.isEmpty()){
            updateUserPassword.setError("Empty");
            updateUserPassword.requestFocus();
        }else if (bitmap == null){
            pd.setMessage("Uploading...");
            pd.show();
            updateData(image);
        }else {
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }
    }
    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Users ").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Update_User.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(DownloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(Update_User.this,"Somthing went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {
        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("password",password);
        hp.put("bike",bike);
        hp.put("image",image);

        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                pd.dismiss();
                Toast.makeText(Update_User.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_User.this,User_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Update_User.this, "Something Want Wrong", Toast.LENGTH_SHORT).show();
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
            updateUserImage.setImageBitmap(bitmap);
        }
    }
}