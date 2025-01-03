package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userinfo;
    FirebaseUser firebaseUser;
    EditText email,name,pass;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userid = mAuth.getUid();
        firebaseUser = mAuth.getCurrentUser();
        userinfo = firebaseDatabase.getReference("users").child(userid);

        userinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        email.setText(mAuth.getCurrentUser().getEmail());
    }
    public void goback(View v){
        startActivity(new Intent(EditInfo.this,AccountDetails.class));
    }

    public void updateall(View v){
        if(!name.getText().toString().isEmpty()){
            userinfo.child("Name").setValue(name.getText().toString());
        }else{
            name.setError("Invalid Name");
            return;
        }
        if(!email.getText().toString().isEmpty()){
            firebaseUser.updateEmail(email.getText().toString());
        }
        else{
            email.setError("Invalid Email");
            return;
        }
        if(!pass.getText().toString().isEmpty()){
            if(pass.getText().toString().length()>=8 ){
                firebaseUser.updatePassword(pass.getText().toString());
            }else{
                pass.setError("Lenght must be greater than 8");
                return;
            }
        }
        Toast.makeText(getApplicationContext(),"Personal Information Updated",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditInfo.this, AccountDetails.class));
    }

}
