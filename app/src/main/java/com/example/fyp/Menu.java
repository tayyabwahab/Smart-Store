package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
    }

    public void enterstore(View v){

        startActivity(new Intent(Menu.this,QRCodegen.class));
    }

    public void scanitem(View v){
       startActivity(new Intent(Menu.this, Scanqr.class));

    }

    public void accountdetails(View v){
        startActivity(new Intent(Menu.this, AccountDetails.class));
    }

    public void topup(View v){
        mAuth.signOut();
        Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Menu.this, MainActivity.class));
    }

}
