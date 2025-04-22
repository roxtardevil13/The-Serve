package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Logout extends AppCompatActivity {
    DrawerLayout drawerLayout;
private Button logout;
private FirebaseAuth au;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout );
        drawerLayout=findViewById( R.id.dl8 );
        logout=findViewById(R.id.button5);
        au=FirebaseAuth.getInstance();
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                au.signOut();
                finish();
                Intent intent = new Intent( Logout.this, Login.class);
                startActivity(intent);
            }
        });
    }
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){HomePage.redirectActivity(this,Login.class);}
    public void ClickRegistration(View view){HomePage.redirectActivity(this,Registration.class); }
    public void ClickLogout(View view){ recreate();}
    public void ClickBook(View view){HomePage .redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }
    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}