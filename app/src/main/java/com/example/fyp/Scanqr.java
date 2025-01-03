package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanqr extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    FirebaseDatabase database;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
        database = FirebaseDatabase.getInstance();
        myref = database.getReference().child("products");
    }

    @Override
    public void handleResult(Result result) {
        final String r = result.getText();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(r)){
                    Intent confirm = new Intent(Scanqr.this,ConfirmOrder.class);
                    confirm.putExtra("result",r);
                    startActivity(confirm);
                }else{
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Scanqr.this);
                    alert.setCancelable(true);
                    alert.setTitle("Invalid Code");
                    alert.setMessage("Please try again with valid code");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Scanqr.this,Menu.class));
                            dialog.cancel();
                        }
                    });

                    alert.show();

                    //Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_LONG).show();
                    ScannerView.stopCamera();
                    onPostResume();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
