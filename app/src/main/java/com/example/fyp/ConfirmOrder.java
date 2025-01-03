package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ConfirmOrder extends AppCompatActivity {

    Bundle result;
    String qrresult;
    TextView nametv,pricetv,totaltv;
    ImageView productimage;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userdetails,productdetails,orders;
    Button dec,inc,number;
    FirebaseUser firebaseUser;
    int quantity = 1;
    long maxid;
    int price,totalprice;
    float currentbalance;
    String userid,productname,productprice,imageurl,productsize;
    String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getUid();
        database = FirebaseDatabase.getInstance();
        productimage = findViewById(R.id.qrimage);
        firebaseUser = mAuth.getCurrentUser();

        orders = FirebaseDatabase.getInstance().getReference().child("Orders");

        orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = dataSnapshot.child(Uid).getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(firebaseUser!=null){
        userid = firebaseUser.getUid();
        }

        userdetails = FirebaseDatabase.getInstance().getReference().child("users").child(userid);

        userdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String balance = dataSnapshot.child("Balance").getValue().toString();
                currentbalance = Float.parseFloat(balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dec = findViewById(R.id.decrement);
        number = findViewById(R.id.quan);
        inc = findViewById(R.id.increment);
        productimage = findViewById(R.id.proimg);
        nametv = findViewById(R.id.productname);
        pricetv = findViewById(R.id.price);
        totaltv = findViewById(R.id.tprice);
        result = getIntent().getExtras();
        if(result!=null) {
            qrresult = result.getString("result");
        }
        productdetails = FirebaseDatabase.getInstance().getReference().child("products").child(qrresult);

        productdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productname = dataSnapshot.child("name").getValue().toString();
                productsize = dataSnapshot.child("size").getValue().toString();
                nametv.setText(productname.concat(" - ").concat(productsize));
                productprice = dataSnapshot.child("price").getValue().toString();
                price = Integer.parseInt(productprice);
                quantity = 1;
                totalprice = price;
                pricetv.setText("PKR ".concat(String.valueOf(price)));
                totaltv.setText("PKR ".concat(String.valueOf(price)));
                imageurl = dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(imageurl).into(productimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void increment(View v){
        quantity = Integer.parseInt(number.getText().toString());
        quantity = quantity+1;
        number.setText(String.valueOf(quantity));
        totalprice = quantity*price;
        totaltv.setText("PKR ".concat(String.valueOf(totalprice)));
    }

    public void decrement(View v){
        quantity = Integer.parseInt(number.getText().toString());
        quantity = quantity-1;
        if(quantity<= 0){
            quantity = 1;
        }
        number.setText(String.valueOf(quantity));
        totalprice = quantity*price;
        totaltv.setText("PKR ".concat(String.valueOf(totalprice)));
    }

    public void confirm(View v){

        if(currentbalance>=totalprice){
            currentbalance = currentbalance-totalprice;
            userdetails.child("Balance").setValue(currentbalance);
            orders.child(Uid).child(String.valueOf(maxid+1)).child("ProductID").setValue(qrresult);
            orders.child(Uid).child(String.valueOf(maxid+1)).child("ProductName").setValue(productname);
            orders.child(Uid).child(String.valueOf(maxid+1)).child("ProductPrice").setValue(productprice);
            orders.child(Uid).child(String.valueOf(maxid+1)).child("ProductQuantity").setValue(quantity);
            orders.child(Uid).child(String.valueOf(maxid+1)).child("TotalAmmount").setValue(totalprice);



            /*lastid.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String oid = dataSnapshot.getValue().toString();
                    int oidint = Integer.parseInt(oid);
                    int nextid = oidint+1;
                    orders.child("orderID").setValue(nextid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

            ////////
            ////////

            Toast.makeText(getApplicationContext(),quantity + "  (" + productname.concat(" - ").concat(productsize).concat(")  purchased"),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Menu.class));
        }else{
            Toast.makeText(getApplicationContext(),"Not Enough Balance",Toast.LENGTH_SHORT).show();
        }
    }
    public void cancel(View v){
        startActivity(new Intent(this, Scanqr.class));
    }

}
