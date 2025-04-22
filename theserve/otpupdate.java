package com.example.theserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class otpupdate  extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private Button mVerifyCodeBtn;
    private EditText otpEdit;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    FirebaseDatabase mrf;
    String otp, verificationId,verification_code ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpupdate);
        drawerLayout = findViewById(R.id.dl22);

        mVerifyCodeBtn = findViewById(R.id.otpu);
        otpEdit = findViewById(R.id.osnum1);

        firebaseAuth = FirebaseAuth.getInstance();

        otp = getIntent().getStringExtra("auth");

        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification_code = otpEdit.getText().toString();
                if (!verification_code.isEmpty()) {
                    validate();
                    otp = verification_code;
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verification_code);
                    signIn(credential);
                } else {
                    Toast.makeText(otpupdate.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void signIn(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // OTP verification successful, update phone number in the Firebase Realtime Database
                    String newPhoneNumber = getIntent().getStringExtra("num");
                    updatePhoneNumberInDatabase(newPhoneNumber);

                    // Display a Toast for successful OTP verification
                    Toast.makeText(otpupdate.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(otpupdate.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(otpupdate.this, ProfileActivity.class));
                }
            }
        });
    }

    private void updatePhoneNumberInDatabase(String newPhoneNumber) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(userId);

        userRef.child("phone").setValue(newPhoneNumber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Phone number updated in the Firebase Realtime Database
                            Toast.makeText(otpupdate.this, "Phone Number Updated Successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // Finish the current activity or perform other actions
                        } else {
                            // Phone number update in the database failed
                            Toast.makeText(otpupdate.this, "Error Updating Phone Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void validate(){
        if (verification_code.isEmpty()){
            Toast.makeText( otpupdate.this, "Please Enter OTP", Toast.LENGTH_SHORT ).show();
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
