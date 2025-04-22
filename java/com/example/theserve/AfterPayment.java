package com.example.theserve;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class AfterPayment extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ImageView iv;
    private TextView h, mt, at,ct;
    Button b;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, email, aboutb, callb;
    String phone="9667078742";
    String n, e, p, a, m;
    boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_after_payment );
        drawerLayout=findViewById( R.id.d11 );
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
                startActivity( new Intent(AfterPayment.this, FeedBack.class) );
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
                    Toast.makeText( AfterPayment.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterPayment.this, AboutUs.class) );
            }
        } );
        Intent i = getIntent();
        n = i.getStringExtra("name");
        e = i.getStringExtra("email");
        p = i.getStringExtra("phone");
        a = i.getStringExtra("address");
        m = i.getStringExtra("requirement");
        b = (Button)findViewById(R.id.qrb);
        iv= (ImageView) findViewById(R.id.qri);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter w = new MultiFormatWriter();
                try {
                    BitMatrix bm = w.encode(n +
                            e +
                            p +
                            a +
                            m,
                            BarcodeFormat.QR_CODE, 500 ,500);
                    BarcodeEncoder bre = new BarcodeEncoder();
                    Bitmap bp = bre.createBitmap(bm);
                    iv.setImageBitmap(bp);
                } catch (WriterException writerException) {
                    writerException.printStackTrace();
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
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){ HomePage.redirectActivity(this,Login.class);}
    public void ClickRegistration(View view){
        HomePage.redirectActivity(this,Registration.class);
    }
    public void ClickLogout(View view){ HomePage.redirectActivity(this,Logout.class);}
    public void ClickBook(View view){HomePage.redirectActivity( this, HomePage.class); }
    public void ClickHome(View view){HomePage.redirectActivity( this, HomePage.class); }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer( drawerLayout );
    }
}