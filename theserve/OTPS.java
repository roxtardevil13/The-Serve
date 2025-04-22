package com.example.theserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

public class OTPS  extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button mVerifyCodeBtn;
    private EditText otpEdit;
    private FirebaseAuth firebaseAuth;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_otps );
        drawerLayout = findViewById( R.id.dl5 );

        mVerifyCodeBtn = findViewById( R.id.otp );
        otpEdit = findViewById( R.id.osnum );

        firebaseAuth = FirebaseAuth.getInstance();

        otp = getIntent().getStringExtra( "auth" );
        mVerifyCodeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verification_code = otpEdit.getText().toString();
                if (!verification_code.isEmpty()) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential( otp, verification_code );
                    signIn( credential );
                } else {
                    Toast.makeText( OTPS.this, "Please Enter OTP", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OTPS.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(OTPS.this , Login.class));
                }else{
                    Toast.makeText(OTPS.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
