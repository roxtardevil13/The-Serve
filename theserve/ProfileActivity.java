 package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



 public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private TextView name, number, email, address, h,mt, at,ct;
    private Button update;
    private FirebaseAuth a;
    private FirebaseDatabase r;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, em, aboutb, callb;
    String phone="9667078742";
    boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        drawerLayout = findViewById( R.id.dl );
        fab = (FloatingActionButton) findViewById( R.id.fab );
        em = (FloatingActionButton) findViewById( R.id.mailbutton );
        mt = (TextView) findViewById( R.id.mailtext );
        aboutb = (FloatingActionButton) findViewById( R.id.aboutbutton );
        at = (TextView) findViewById( R.id.abouttext );
        callb = (FloatingActionButton) findViewById( R.id.callbutton );
        ct = (TextView) findViewById( R.id.calltext );
        h = (TextView) findViewById(R.id.texthelp);
        fabopen = AnimationUtils.loadAnimation( this, R.anim.fab_open );
        fabclose = AnimationUtils.loadAnimation( this, R.anim.fab_close );
        rf = AnimationUtils.loadAnimation( this, R.anim.rotate_forward );
        rb = AnimationUtils.loadAnimation( this, R.anim.rotate_backward );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animfab();
            }
        } );
        em.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( ProfileActivity.this, FeedBack.class ) );
            }
        } );
        callb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Intent.ACTION_DIAL );
                i.setData( Uri.parse( "tel:" + phone ) );
                if (i.resolveActivity( getPackageManager() ) != null) {
                    startActivity( i );
                } else {
                    Toast.makeText( ProfileActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( ProfileActivity.this, AboutUs.class ) );
            }
        } );
            pa();

        a = FirebaseAuth.getInstance();
        r = FirebaseDatabase.getInstance();
        FirebaseUser u= a.getCurrentUser();
        if (u != null) {
        DatabaseReference rf = r.getReference("user").child(u.getUid());
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile up = snapshot.getValue(UserProfile.class);
                if (up != null) {
                    name.setText(up.getName());
                    number.setText(up.getPhone());
                    email.setText(up.getEmail());
                    address.setText(up.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(ProfileActivity.this, UpdateUser.class) );
            }
        } );
    }
    private void animfab(){
        if(isOpen){
            fab.startAnimation( rf );
            em.startAnimation( fabclose );
            aboutb.startAnimation( fabclose );
            callb.startAnimation( fabclose );
            mt.startAnimation( fabclose);
            at.startAnimation( fabclose );
            ct.startAnimation( fabclose );
            h.startAnimation(fabopen);
            em.setClickable( false );
            mt.setClickable( false );
            aboutb.setClickable( false );
            at.setClickable( false );
            callb.setClickable( false );
            ct.setClickable( false );
            h.setClickable(true);
            isOpen=false;
        }else{
            fab.startAnimation( rb );
            em.startAnimation( fabopen );
            aboutb.startAnimation( fabopen );
            callb.startAnimation( fabopen );
            mt.startAnimation( fabopen );
            at.startAnimation( fabopen );
            ct.startAnimation( fabopen );
            em.setClickable( true );
            mt.setClickable( true );
            aboutb.setClickable( true );
            at.setClickable( true );
            callb.setClickable( true );
            ct.setClickable( true );
            h.setClickable(false);
            isOpen=true;
        }
    }
        private void pa(){
        name = (TextView) findViewById(R.id.pName);
        number = (TextView) findViewById(R.id.pNum);
        email = (TextView) findViewById(R.id.pEmail);
        address = (TextView) findViewById( R.id.addresst );
        update = (Button) findViewById( R.id.ub );
        }
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){recreate();}
    public void ClickRegistration(View view){
        HomePage.redirectActivity(this,Registration.class);
    }
    public void ClickLogout(View view){ HomePage.redirectActivity(this,Logout.class); }
    public void ClickBook(View view){HomePage .redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }
    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}