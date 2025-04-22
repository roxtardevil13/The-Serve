package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import static androidx.core.content.ContextCompat.startActivity;

public class HomePage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ImageView a,b,c,d,e,f;
    private TextView h,mt,at,ct;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, email, aboutb, callb;
    String phone="9667078742";
    boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnv= findViewById(R.id.bottomnav);
        bnv.setSelectedItemId(R.id.home);
        bnv.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else if (itemId == R.id.logout) {
                    startActivity(new Intent(getApplicationContext(), Logout.class));
                }
            }
        });
        drawerLayout=findViewById( R.id.dlHome);
        hp();
        fab= (FloatingActionButton)  findViewById( R.id.fab);
        email = (FloatingActionButton) findViewById( R.id.mailbutton );
        mt = (TextView)findViewById( R.id.mailtext );
        aboutb = (FloatingActionButton) findViewById( R.id.aboutbutton );
        at = (TextView)findViewById( R.id.abouttext );
        callb = (FloatingActionButton) findViewById( R.id.callbutton );
        ct = (TextView)findViewById( R.id.calltext );
        h = (TextView) findViewById(R.id.texthelp);
        fabopen = AnimationUtils.loadAnimation( this, R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation( this, R.anim.fab_close);
        rf= AnimationUtils.loadAnimation( this, R.anim.rotate_forward);
        rb= AnimationUtils.loadAnimation( this, R.anim.rotate_backward);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animfab();
            }
        } );
        email.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomePage.this, FeedBack.class) );
            }
        } );
        callb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData( Uri.parse("tel:"+phone) );
                if(i.resolveActivity( getPackageManager()) != null){
                    startActivity( i );
                }else{
                    Toast.makeText( HomePage.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, AboutUs.class) );
            }
        } );
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid1.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid2.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid3.class));
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid4.class));
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid5.class));
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Maid6.class));
            }
        });
    }
    private void hp(){
        a=(ImageView)findViewById(R.id.lux);
        b=(ImageView)findViewById(R.id.nany);
        c=(ImageView)findViewById(R.id.clean);
        d=(ImageView)findViewById(R.id.house);
        e =(ImageView)findViewById(R.id.cook);
        f =(ImageView)findViewById(R.id.vip);
    }
    private void animfab(){
        if(isOpen){
            fab.startAnimation( rf );
            email.startAnimation( fabclose );
            aboutb.startAnimation( fabclose );
            callb.startAnimation( fabclose );
            mt.startAnimation( fabclose);
            at.startAnimation( fabclose );
            ct.startAnimation( fabclose );
            h.startAnimation(fabopen);
            email.setClickable( false );
            mt.setClickable( false );
            aboutb.setClickable( false );
            at.setClickable( false );
            callb.setClickable( false );
            ct.setClickable( false );
            h.setClickable(true);
            isOpen=false;
        }else{
            fab.startAnimation( rb );
            email.startAnimation( fabopen );
            aboutb.startAnimation( fabopen );
            callb.startAnimation( fabopen );
            mt.startAnimation( fabopen );
            at.startAnimation( fabopen );
            ct.startAnimation( fabopen );
            h.startAnimation(fabclose);
            email.setClickable( true );
            mt.setClickable( true );
            aboutb.setClickable( true );
            at.setClickable( true );
            callb.setClickable( true );
            ct.setClickable( true );
            h.setClickable(false);
            isOpen=true;
        }
    }
        public void ClickMenu (View view){
            openDrawer(drawerLayout);
        }

        public static void openDrawer (DrawerLayout drawerLayout){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        public void ClickLogo (View view){
            closeDrawer(drawerLayout);
        }

        public static void closeDrawer (DrawerLayout drawerLayout){
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
        public void ClickHome (View view){
        recreate();
    }
        public void ClickProfile (View view){
            redirectActivity(this, Login.class);
        }
        public void ClickRegistration (View view){
            redirectActivity(this, Registration.class);
        }
        public void ClickLogout (View view){
            redirectActivity(this, Logout.class);
        }
        public void ClickBook (View view){
            recreate();
        }
        public static void redirectActivity (Activity activity, Class aclass){
            Intent i = new Intent(activity, aclass);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(i);
        }

        @Override
        protected void onPause () {
            super.onPause();
            closeDrawer(drawerLayout);
        }
    }