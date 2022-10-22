package com.krish.yamaha_admin.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.krish.yamaha_admin.MainActivity;
import com.krish.yamaha_admin.R;

public class Login_Activity extends AppCompatActivity {

    private TextInputLayout loginEmail,loginPassword;
    private TextView forgotPassword;
    private Button loginButton;
    private TextView loginRegister;
    private LinearLayout loginLayout;
    private AnimationDrawable animationDrawable;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.loginEmail);
        loginLayout = findViewById(R.id.loginLayout);

        animationDrawable = (AnimationDrawable) loginLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        forgotPassword = findViewById(R.id.forgotPassword);

        auth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,Forgot_Activity.class);
                startActivity(intent);
                finish();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        String email = loginEmail.getEditText().getText().toString();
        String password = loginPassword.getEditText().getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(Login_Activity.this, "Please Fill All the Forms...", Toast.LENGTH_SHORT).show();
        }else {
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(loginEmail.getEditText().getText().toString(),loginPassword.getEditText().getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login_Activity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Activity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser()!=null){
            Intent intent = new Intent(Login_Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}