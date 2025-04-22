package com.example.theserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    private Button pay;
    private FirebaseAuth fba;
    private TextView h,mt, at,ct, name, em, ph, address, req;
    Animation fabopen, fabclose, rf, rb;
    FloatingActionButton fab, email, aboutb, callb;
    String phone="9667078742";
    String n, e, p, a, m;
    boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payment );
        userpayment();
        Intent i = getIntent();
        n = i.getStringExtra("name");
        e = i.getStringExtra("email");
        p = i.getStringExtra("phone");
        a = i.getStringExtra("address");
        m = i.getStringExtra("requirement");
        name.setText(n);
        em.setText(e);
        ph.setText(p);
        address.setText(a);
        req.setText(m);
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
                startActivity( new Intent(Payment.this, FeedBack.class) );
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
                    Toast.makeText( Payment.this, "There is no app that support this action", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        aboutb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this, AboutUs.class) );
            }
        } );
        fba = FirebaseAuth.getInstance();
        Checkout.preload( getApplicationContext() );
       // checkout.setKeyID("rzp_test_pLHRM5lSDvJ2mb");
        pay = findViewById( R.id.button );

        FirebaseUser User = fba.getCurrentUser();
        pay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User == null) {
                    startActivity( new Intent( Payment.this, Login.class ) );
                } else {
                    startPayment();
                }
            }
        } );
    }

    private void userpayment() {
        name= (TextView)findViewById(R.id.pyName) ;
        em= (TextView)findViewById(R.id.pyEmail) ;
        ph= (TextView)findViewById(R.id.pyPhone) ;
        address  = (TextView)findViewById(R.id.pyAddress) ;
        req = (TextView)findViewById(R.id.pyReq) ;
    }


    private void startPayment() {
        final   AppCompatActivity activity = this;
        final Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_pLHRM5lSDvJ2mb");



         JSONObject object = new JSONObject();
         try{
            object.put( "name", "The Serve" );
            object.put( "description", "Maid" );
            object.put( "image",R.drawable.maid );
          // options.put( "order_id", "order_DBJOWzybf0sJbb" );//from response of step 3.
            object.put( "theme.color", "#3399cc" );
            object.put( "currency", "INR" );
            object.put( "amount", "100000" );//pass amount in currency subunits
           JSONObject preFill = new JSONObject();
            preFill.put( "email", "kr355571@gmail.com" );
            preFill.put( "contact", "9667078742" );
            object.put("preFill",preFill);
            checkout.open( activity, object );
        } catch (Exception e) {
           Toast.makeText( activity, "Exception"+e.getMessage(), Toast.LENGTH_SHORT ).show();
           e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    @Override
    public void onPaymentSuccess(String razorPayId) {
        Toast.makeText( this, "Payment Successful"+razorPayId, Toast.LENGTH_SHORT ).show();
        Intent i = new Intent(Payment.this, AfterPayment.class);
        i.putExtra("name", n);
        i.putExtra("email", e);
        i.putExtra("phone", p);
        i.putExtra("address", a);
        i.putExtra("req", m);
        startActivity(i);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText( this, "Payment Failed"+s, Toast.LENGTH_SHORT ).show();

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
}