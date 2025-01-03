package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodegen extends AppCompatActivity {

    TextView loadingtext;
    String userid;
    Bitmap bitmap;
    String savePath;
    ImageView qrimage;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcodegen);

        mAuth = FirebaseAuth.getInstance();
        qrimage = findViewById(R.id.qrimage);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        savePath = directory.toString();
        loadingtext = findViewById(R.id.loadingtext);

        if(mAuth.getCurrentUser() != null){
            userid = mAuth.getCurrentUser().getUid();
        }

        if(userid.length()>0) {
            QRGEncoder qrgEncoder = new QRGEncoder(userid, null, QRGContents.Type.TEXT, 800);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                Toast.makeText(getApplicationContext(),"QRCode generated", Toast.LENGTH_SHORT).show();
                qrimage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void goback(View v){
        startActivity(new Intent(this, Menu.class));
    }
}