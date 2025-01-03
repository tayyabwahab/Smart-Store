package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email, password;
    String stremail,strpassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);
        password = findViewById(R.id.password);
    }

    public void signin(View v){

        progressBar.setVisibility(View.VISIBLE);
        stremail = email.getText().toString().toLowerCase().trim();
        strpassword = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(stremail,strpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Logged in", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, Menu.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials!!!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void register(View v){
        Intent register = new Intent(this, register.class);
        startActivity(register);
    }

}
