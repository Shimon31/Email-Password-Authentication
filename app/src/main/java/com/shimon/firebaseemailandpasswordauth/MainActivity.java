package com.shimon.firebaseemailandpasswordauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        INIT();
        checkVerification();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Verification link sent Successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        
    }

    private void checkVerification() {


        if (!firebaseUser.isEmailVerified()){
            textView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
        else {
            textView.setText("Email Verified");
            button.setVisibility(View.GONE);
        }
    }


    private void INIT() {
        textView = findViewById(R.id.verifyTV);
        button = findViewById(R.id.verifyBTN);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logOUT(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkVerification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkVerification();
    }
}