package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    EditText email,password,name;
    String semail,pword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference().child("users");
    }

    public void registered(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void signup(View v){

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        semail = email.getText().toString().toLowerCase().trim();
        pword = password.getText().toString().trim();

        if(TextUtils.isEmpty(semail)){
            email.setError("Invalid Name");
            return;
        }
        if(TextUtils.isEmpty(name.getText().toString())){
            name.setError("Invalid Email");
            return;
        }
        if(TextUtils.isEmpty(pword)){
            password.setError("Required");
            return;
        }
        if(pword.length()<8){
            password.setError("Length must be at least 8 ");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(semail,pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.signInWithEmailAndPassword(semail,pword);
                            String userid = mAuth.getCurrentUser().getUid();

                            users.child(userid).child("Name").setValue(name.getText().toString());
                            users.child(userid).child("Email").setValue(semail);
                            users.child(userid).child("Balance").setValue(0);

                            mAuth.signOut();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(register.this,MainActivity.class));
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(register.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}