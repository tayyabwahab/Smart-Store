package com.example.fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class InvalidCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_code);

        final AlertDialog.Builder alert = new AlertDialog.Builder(InvalidCode.this);
        alert.setCancelable(true);
        alert.setTitle("Invalid Code");
        alert.setMessage("Please try again with valid code");
        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(InvalidCode.this,Menu.class));
                dialog.cancel();
            }
        });

        alert.show();

    }
}
