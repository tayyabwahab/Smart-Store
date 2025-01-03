package com.example.fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountDetails extends AppCompatActivity {

    String[] array = {"Edit Personal Information","Deposit","Delete Account"};
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(AccountDetails.this,EditInfo.class));
                }
                if(position==1){
                    startActivity(new Intent(AccountDetails.this,Deposit.class));
                }
                if(position==2){
                    AlertDialog.Builder alert = new AlertDialog.Builder(AccountDetails.this);
                    alert.setCancelable(true);
                    alert.setTitle("Confirm");
                    alert.setMessage("Press Confirm to delete your account.");
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String uid = mAuth.getCurrentUser().getUid();
                            databaseReference.child(uid).removeValue();
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.delete();
                            mAuth.signOut();
                            startActivity(new Intent(AccountDetails.this,MainActivity.class));
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alert.show();
                }
            }
        });

    }

    public void goback(View v){
        startActivity(new Intent(AccountDetails.this, Menu.class));
    }
}
