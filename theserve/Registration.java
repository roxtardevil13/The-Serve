package com.example.theserve;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private EditText Name, Phone, countryCode, Email, Password, Address;
    private TextView processText, h, mt,at,ct;
    private Button Register, Login;
    private FirebaseAuth mAuth;
    private DatabaseReference mrf, newref;
    private FirebaseDatabase fbd;
    String name, phone, email, password, address, CC;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, em, aboutb, callb;
    String ph="9667078742";
    boolean isOpen=false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private static final String TAG = "Registration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );
        form();
        drawerLayout=findViewById( R.id.dl3 );
        fab= (FloatingActionButton)  findViewById( R.id.fab);
        em = (FloatingActionButton) findViewById( R.id.mailbutton );
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
        em.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Registration.this, FeedBack.class) );
            }
        } );
        callb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData( Uri.parse("tel:"+ph) );
                if(i.resolveActivity( getPackageManager()) != null){
                    startActivity( i );
                }else{
                    Toast.makeText( Registration.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, AboutUs.class) );
            }
        } );

        mAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Registration.this, Login.class );
                startActivity( intent );
            }
        } );
        Register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String uEmail = Email.getText().toString().trim();
                    String upassword = Password.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword( uEmail, upassword ).addOnCompleteListener( new OnCompleteListener < AuthResult >() {
                        @Override
                        public void onComplete(@NonNull Task < AuthResult > task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                                startActivity( new Intent( Registration.this, OTPS.class ) );
                            } else {
                                Toast.makeText( Registration.this, "Registration Failed", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );
                }
                String country_code = countryCode.getText().toString();
                String phone = Phone.getText().toString();
                String phoneNumber = "+" + country_code + "" + phone;
                if (!country_code.isEmpty() || !phone.isEmpty()) {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder( mAuth )
                            .setPhoneNumber( phoneNumber )
                            .setTimeout( 60L, TimeUnit.SECONDS )
                            .setActivity( Registration.this )
                            .setCallbacks( mCallBacks )
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber( options );
                } else{
                    processText.setText("Check Again !");
                    processText.setTextColor( Color.RED );
                    processText.setVisibility( View.VISIBLE );
                }
            }
        } );
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                signIn( phoneAuthCredential );
                processText.setText( "OTP has been Sent" );
                processText.setVisibility( View.VISIBLE );
                
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                processText.setText( e.getMessage() );
                processText.setTextColor( Color.RED );
                processText.setVisibility( View.VISIBLE );
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent( s, forceResendingToken );

                //sometime the code is not detected automatically
                //so user has to manually enter the code
                processText.setText( "OTP has been Sent" );
                processText.setVisibility( View.VISIBLE );
                new Handler( Looper.getMainLooper() ).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent = new Intent( Registration.this, OTPS.class );
                        otpIntent.putExtra( "auth", s );
                        startActivity( otpIntent );
                    }
                }, 1000 );

            }
        };
    }

    private void form() {
        Name = (EditText) findViewById( R.id.enName );
        Phone = (EditText) findViewById( R.id.enPhone );
        Email = (EditText) findViewById( R.id.enEmail );
        Password = (EditText) findViewById( R.id.enPassword );
        Register = (Button) findViewById( R.id.enRegister );
        Login = (Button) findViewById( R.id.enLogin );
       processText = findViewById( R.id.display1 );
       countryCode = findViewById( R.id.code1 );
       Address = (EditText)findViewById( R.id.adtext );

    }

    private boolean validate() {
        boolean result = false;
        name = Name.getText().toString();
        phone = Phone.getText().toString();
        address = Address.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        CC = countryCode.getText().toString();
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || CC.isEmpty()) {
            Toast.makeText( this, "Please Enter all the details", Toast.LENGTH_SHORT ).show();
        } else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification() {
        FirebaseUser fbu = mAuth.getCurrentUser();
        if (fbu != null) {
            fbu.sendEmailVerification().addOnCompleteListener( new OnCompleteListener < Void >() {
                @Override
                public void onComplete(@NonNull Task < Void > task) {
                    if (task.isSuccessful()) {
                        sud();
                        Toast.makeText( Registration.this, "You're Registered, Verification Mail & OTP has been sent", Toast.LENGTH_SHORT ).show();
                        mAuth.signOut();
                        finish();
                       startActivity( new Intent( Registration.this, OTPS.class ) );
                    } else {
                        Toast.makeText( Registration.this, "Verification Mail has't been sent", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }
    }

    private void sud() {
        fbd = FirebaseDatabase.getInstance();
        mrf = fbd.getReference("user" );
        newref = mrf.child( mAuth.getUid());
        UserProfile up = new UserProfile( name, phone, email, address );
        newref.setValue( up );
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        finish();
        Toast.makeText( this, "You are already Registered, Kindly Logout for new Registration", Toast.LENGTH_SHORT ).show();
    }

    private void signIn(PhoneAuthCredential credential) {
       mAuth.signInWithCredential( credential ).addOnCompleteListener( new OnCompleteListener < AuthResult >() {
            @Override
            public void onComplete(@NonNull Task < AuthResult > task) {
                if (task.isSuccessful()) {
                    mAuth.signOut();
                    finish();
                    Toast.makeText( Registration.this, "OTP Verification Successfull, Verify from registered Email", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent(Registration.this, Login.class) );
                } else {
                    processText.setText( task.getException().getMessage() );
                    processText.setTextColor( Color.RED );
                    processText.setVisibility( View.VISIBLE );
                }
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
            h.startAnimation(fabclose);
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
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){HomePage .redirectActivity( this, Login.class);}
    public void ClickRegistration(View view){
       recreate();
    }
    public void ClickLogout(View view){HomePage.redirectActivity(this,Logout.class);}
    public void ClickBook(View view){HomePage .redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage .redirectActivity( this, HomePage.class); }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}
