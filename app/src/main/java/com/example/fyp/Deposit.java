package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Deposit extends AppCompatActivity {

    EditText ammount;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    TextView cb;
    int currentbalance,newbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ammount = findViewById(R.id.ammount);
        cb= findViewById(R.id.cbalance);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentbalance = Integer.parseInt(dataSnapshot.child("Balance").getValue().toString());
                cb.setText("Current Balance (PKR): " + currentbalance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void makedeposit(View v){
        newbalance = currentbalance + Integer.parseInt(ammount.getText().toString());
        databaseReference.child("Balance").setValue(newbalance);
        Toast.makeText(getApplicationContext(),ammount.getText().toString().concat(" PKR successfully added to your account"),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Deposit.this,AccountDetails.class));
    }

    public void returnt(View v){
        startActivity(new Intent(Deposit.this, AccountDetails.class));
    }
}
