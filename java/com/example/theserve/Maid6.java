package com.example.theserve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Maid6 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button b;
    EditText name, email,phone, address;
    ImageView u,d;
    String n,e,p,a, m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.maid_vip);
        drawerLayout=findViewById( R.id.dlvip );
        user();
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Maid6.this, "You Required Maid on Urgent basis", Toast.LENGTH_SHORT).show();
                m = "Maid Needed on Urgent Basis";
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Maid6.this, "You Required Maid on Daily basis", Toast.LENGTH_SHORT).show();
                m = "Maid Needed on Daily Basis";
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                n = name.getText().toString();
                e = email.getText().toString();
                p = phone.getText().toString();
                a = address.getText().toString();
                if (m == null) {
                    Toast.makeText(Maid6.this, "Kindly select the requirement basis", Toast.LENGTH_SHORT).show();
                } else if (n.isEmpty() || e.isEmpty() || p.isEmpty() || a.isEmpty()) {
                    Toast.makeText(Maid6.this, "Kindly fill all the information fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Maid6.this, Payment.class);
                    i.putExtra("name", n);
                    i.putExtra("email", e);
                    i.putExtra("phone", p);
                    i.putExtra("address", a);
                    i.putExtra("requirement", m);
                    startActivity(i);
                }
            }
        });
    }

    private void user() {
        name=(EditText) findViewById(R.id.vName);
        email=(EditText) findViewById(R.id.vEmail);
        phone=(EditText) findViewById(R.id.vPhone);
        address=(EditText)findViewById(R.id.vAddress);
        u = (ImageView) findViewById(R.id.vU);
        d = (ImageView) findViewById(R.id.vD);
        b=(Button)findViewById(R.id.vbutton);
    }

    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){HomePage .redirectActivity( this, Login.class);}
    public void ClickRegistration(View view){
        HomePage.redirectActivity(this,Registration.class);
    }
    public void ClickLogout(View view){ HomePage.redirectActivity(this,Logout.class); }
    public void ClickBook(View view){HomePage.redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }
    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}
