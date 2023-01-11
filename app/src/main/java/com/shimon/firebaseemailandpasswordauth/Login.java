package com.shimon.firebaseemailandpasswordauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText emailEt,passwordEt;
    Button loginButton;
    ProgressBar progressBar;
    String email, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();

        autoLogin();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();

                if (email.isEmpty()){
                    emailEt.setError("Enter Valid Email");
                }
                else if (password.isEmpty() || password.length() <6){
                    passwordEt.setError("Enter 6 digit Password");
                }
                else
                {
                    login();
                }

            }
        });
    }

    private void autoLogin() {
        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(Login.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });

    }

    private void Init() {
        emailEt = findViewById(R.id.lEmailET);
        passwordEt = findViewById(R.id.lPassET);
        loginButton = findViewById(R.id.loginBTN);
        progressBar = findViewById(R.id.loadingBar);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerBtn(View view) {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }
}