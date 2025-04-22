package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about_us );
        drawerLayout=findViewById( R.id.da );
    }
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){HomePage.redirectActivity( this, Login.class);}
    public void ClickRegistration(View view){
        HomePage.redirectActivity(this,Registration.class);
    }
    public void ClickLogout(View view){HomePage.redirectActivity(this,Logout.class); }
    public void ClickBook(View view){HomePage .redirectActivity( this, HomePage.class);
    }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }
    @Override
    protected void onPause() {
        super.onPause();
       HomePage.closeDrawer( drawerLayout );
    }
}