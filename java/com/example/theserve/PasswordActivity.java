package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class PasswordActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private EditText Email;
    private Button Reset;
    private FirebaseAuth rpw;
    private TextView h,mt, at,ct;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, email, aboutb, callb;
    String phone="9667078742";
    boolean isOpen=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        drawerLayout=findViewById( R.id.dl1 );
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
                startActivity( new Intent(PasswordActivity.this, FeedBack.class) );
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
                    Toast.makeText( PasswordActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordActivity.this, AboutUs.class) );
            }
        } );
        resetpassword();
        rpw = FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String uEmail = Email.getText().toString().trim();
                    if(uEmail.equals("")){
                        Toast.makeText(PasswordActivity.this, "Please Enter your Registered Email", Toast.LENGTH_SHORT).show();
                    }else{
                        rpw.sendPasswordResetEmail(uEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(PasswordActivity.this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(PasswordActivity.this, Login.class));
                                }else{
                                    Toast.makeText(PasswordActivity.this, "Error in Sending Password Reset Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            }
        });
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
    private void resetpassword() {
        Email = (EditText) findViewById(R.id.rEmail);
        Reset = (Button) findViewById(R.id.Verify );
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
    public void ClickBook(View view){HomePage .redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }
    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}