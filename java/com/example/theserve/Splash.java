package com.example.theserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Splash extends AppCompatActivity {
    Animation anim;
    ImageView imageView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        VideoView v = findViewById( R.id.vv );
           v.setVideoPath( "android.resource://" + getPackageName() + "/" + R.raw.broom );
            MediaController mc=new MediaController(this);
           mc.setAnchorView( v );
            mc.setVisibility( v.GONE );
            mc.setBackgroundColor(Color.TRANSPARENT);
           v.setMediaController( mc );
          v.requestFocus();
            v.start();
            v.setBackgroundColor(Color.TRANSPARENT);
            imageView = (ImageView) findViewById( R.id.imageView2 );
            tv = (TextView) findViewById(R.id.textView4);// Declare an imageView to show the animation.
            anim = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.fade_in); // Create the animation.
            anim.setAnimationListener( new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity( new Intent( Splash.this, HomePage.class ) );

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            } );
            imageView.startAnimation( anim );
             tv.startAnimation( anim );
        }
    }
