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
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText nameEt,emailEt,nPassEt,cPassEt;
    Button register;
    ProgressBar progressBar;
    String name,email,password,conPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        INIT();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               name = nameEt.getText().toString();
               email = emailEt.getText().toString();
               password = nPassEt.getText().toString();
               conPassword = cPassEt.getText().toString();

               if (name.isEmpty()){
                   nameEt.setError("Enter Your Name");
               }
               else if (email.isEmpty())
               {
                   emailEt.setError("Enter Your Email");
               }
               else if (password.isEmpty() || password.length() < 6)
               {
                   nPassEt.setError("Enter 6 digit Password");
               }
               else if (conPassword.isEmpty() || conPassword.length() < 6)
               {
                   cPassEt.setError("Enter Confirm Password");
               }
               else if (!password.equals(conPassword))
               {
                   cPassEt.setError("Confirm Password Dosent Match");
               }
               else
               {
                   SignUpUser();
               }
            }
        });

    }

    private void SignUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Register.this, "Verification link sent Successfully", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    Toast.makeText(Register.this, "Sign Up SuccessFully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Register.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);

            }
        });

    }

    private void INIT() {
        nameEt = findViewById(R.id.rNameET);
        emailEt = findViewById(R.id.rEmailET);
        nPassEt = findViewById(R.id.rNPassET);
        cPassEt = findViewById(R.id.rCPassET);
        register = findViewById(R.id.registerBTN);
        progressBar = findViewById(R.id.loadingBar);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}