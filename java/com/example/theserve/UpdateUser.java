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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class UpdateUser extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView processText, h, mt, at, ct;
    EditText Name, Phone, Email, Address, CC;
    String name, phone, email,  address, cc;
    String phoneNumber =cc+phone;
    Button un, uc, ua,  up;
    FirebaseAuth a;
    FirebaseDatabase mrf;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, em, aboutb, callb;
    String ph = "9667078742";
    boolean isOpen = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private static final String TAG = "Updation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        uu();
        validate();
        drawerLayout = findViewById(R.id.dl10);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        em = (FloatingActionButton) findViewById(R.id.mailbutton);
        mt = (TextView) findViewById(R.id.mailtext);
        aboutb = (FloatingActionButton) findViewById(R.id.aboutbutton);
        at = (TextView) findViewById(R.id.abouttext);
        callb = (FloatingActionButton) findViewById(R.id.callbutton);
        ct = (TextView) findViewById(R.id.calltext);
        h = (TextView) findViewById(R.id.texthelp);
        fabopen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rf = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rb = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animfab();
            }
        });
        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateUser.this, FeedBack.class));
            }
        });
        callb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + ph));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Toast.makeText(UpdateUser.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
        });
        aboutb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateUser.this, AboutUs.class));
            }
        });
        un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namechange()) {
                    Toast.makeText(UpdateUser.this, "Name Changed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUser.this, "Some Error Occured in changing Name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addresschange()) {
                    Toast.makeText(UpdateUser.this, "Address Changed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUser.this, "Some Error Occured in changing Address!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        a = FirebaseAuth.getInstance();
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nemail = Email.getText().toString().trim();
                if(nemail.equals("")){
                    Toast.makeText(UpdateUser.this, "Please Enter your Registered Email", Toast.LENGTH_SHORT).show();
                }else{
                    a.sendPasswordResetEmail(nemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UpdateUser.this, "Password Reset Email Sent, Kindly Login again after updating!", Toast.LENGTH_SHORT).show();
                                a.signOut();
                                finish();
                                startActivity(new Intent(UpdateUser.this, Login.class));
                            }else{
                                Toast.makeText(UpdateUser.this, "Error in Sending Password Reset Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        uc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country_code = CC.getText().toString();
                String phone = Phone.getText().toString();
                String phoneNumber = "+" + country_code + "" + phone;
                if (!country_code.isEmpty() || !phone.isEmpty()) {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder( a )
                            .setPhoneNumber( phoneNumber )
                            .setTimeout( 60L, TimeUnit.SECONDS )
                            .setActivity( UpdateUser.this )
                            .setCallbacks( mCallBacks )
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber( options );
                } else{
                    processText.setText("Check Again !");
                    processText.setTextColor( Color.RED );
                    processText.setVisibility( View.VISIBLE );
                }
            }
        });
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
                        Intent otpIntent = new Intent( UpdateUser.this, otpupdate.class );
                        otpIntent.putExtra( "auth", s );
                        otpIntent.putExtra( "num", phone );
                        startActivity( otpIntent );
                    }
                }, 1000 );

            }
        };
    }

    public boolean validate() {
        boolean result = false;
        name = Name.getText().toString();
        phone = Phone.getText().toString();
        address = Address.getText().toString();
        email = Email.getText().toString();
        cc = CC.getText().toString();
        phoneNumber = cc+phone;
        if (name.isEmpty()) {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Phone is empty", Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Address is empty", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
    private void signIn(PhoneAuthCredential credential) {
        a.signInWithCredential( credential ).addOnCompleteListener( new OnCompleteListener < AuthResult >() {
            @Override
            public void onComplete(@NonNull Task < AuthResult > task) {
                if (task.isSuccessful()) {
                    a.signOut();
                    finish();
                    Toast.makeText( UpdateUser.this, "OTP Verification Successfull!", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent(UpdateUser.this, ProfileActivity.class) );
                } else {
                    processText.setText( task.getException().getMessage() );
                    processText.setTextColor( Color.RED );
                    processText.setVisibility( View.VISIBLE );
                }
            }
        } );
    }

    private void animfab() {
        if (isOpen) {
            fab.startAnimation(rf);
            em.startAnimation(fabclose);
            aboutb.startAnimation(fabclose);
            callb.startAnimation(fabclose);
            mt.startAnimation(fabclose);
            at.startAnimation(fabclose);
            ct.startAnimation(fabclose);
            h.startAnimation(fabopen);
            em.setClickable( false );
            mt.setClickable( false );
            aboutb.setClickable( false );
            at.setClickable( false );
            callb.setClickable( false );
            ct.setClickable( false );
            h.setClickable(true);
            isOpen = false;
        } else {
            fab.startAnimation(rb);
            em.startAnimation(fabopen);
            aboutb.startAnimation(fabopen);
            callb.startAnimation(fabopen);
            mt.startAnimation(fabopen);
            at.startAnimation(fabopen);
            ct.startAnimation(fabopen);
            h.startAnimation(fabclose);
            em.setClickable( true );
            mt.setClickable( true );
            aboutb.setClickable( true );
            at.setClickable( true );
            callb.setClickable( true );
            ct.setClickable( true );
            h.setClickable(false);
            isOpen = true;
        }
    }

    private boolean namechange() {
        a = FirebaseAuth.getInstance();
        mrf = FirebaseDatabase.getInstance();
        DatabaseReference ref = mrf.getReference("user");
        if (!name.equals(Name.getEditableText().toString())) {
            ref.child(a.getUid()).child("name").setValue(Name.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean addresschange() {
        a = FirebaseAuth.getInstance();
        mrf = FirebaseDatabase.getInstance();
        DatabaseReference ref = mrf.getReference("user");
        if (!address.equals(Address.getText().toString())) {
            ref.child(a.getUid()).child("address").setValue(Address.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    public void uu(){
        Name = (EditText) findViewById(R.id.uName);
        Phone = (EditText) findViewById(R.id.uPhone);
        Address = (EditText) findViewById( R.id.uAddress );
        CC = (EditText) findViewById( R.id.uCode );
        Email = (EditText) findViewById( R.id.uEmail );
        processText = findViewById( R.id.ut );
        un = (Button)findViewById( R.id.ubName);
        uc = (Button)findViewById(R.id.ubContact);
        ua = (Button)findViewById(R.id.ubAddress);
        up = (Button)findViewById(R.id.ubPassword);


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