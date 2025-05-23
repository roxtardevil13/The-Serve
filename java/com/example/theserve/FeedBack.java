package com.example.theserve;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedBack extends AppCompatActivity {
    private Activity activity;

    DrawerLayout drawerLayout;


    @Override


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_feed_back);

        drawerLayout=findViewById( R.id.dl9);


        final EditText your_name        = (EditText) findViewById(R.id.your_name);


        final EditText your_email       = (EditText) findViewById(R.id.your_email);


        final EditText your_subject     = (EditText) findViewById(R.id.your_subject);


        final EditText your_message     = (EditText) findViewById(R.id.your_message);








        Button email = (Button) findViewById(R.id.post_message);


        email.setOnClickListener(new View.OnClickListener() {


            @Override


            public void onClick(View v) {




                String name      = your_name.getText().toString();


                String email     = your_email.getText().toString();


                String subject   = your_subject.getText().toString();


                String message   = your_message.getText().toString();


                if (TextUtils.isEmpty(name)){


                    your_name.setError("Enter Your Name");


                    your_name.requestFocus();


                    return;


                }




                Boolean onError = false;


                if (!isValidEmail(email)) {


                    onError = true;


                    your_email.setError("Invalid Email");


                    return;


                }




                if (TextUtils.isEmpty(subject)){


                    your_subject.setError("Enter Your Subject");


                    your_subject.requestFocus();


                    return;


                }




                if (TextUtils.isEmpty(message)){


                    your_message.setError("Enter Your Message");


                    your_message.requestFocus();


                    return;


                }




                Intent sendEmail = new Intent( Intent.ACTION_SEND);




                /* Fill it with Data */


                sendEmail.setType("plain/text");


                sendEmail.putExtra( Intent.EXTRA_EMAIL, new String[]{"kr355571@gmail.com"});


                sendEmail.putExtra( Intent.EXTRA_SUBJECT, subject);


                sendEmail.putExtra( Intent.EXTRA_TEXT,


                        "name:"+name+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+message);




                /* Send it off to the Activity-Chooser */


                startActivity(Intent.createChooser(sendEmail, "Send mail..."));






            }


        });


    }




    @Override


    public void onResume() {


        super.onResume();


        //Get a Tracker (should auto-report)






    }




    @Override


    protected void onStart() {


        super.onStart();




    }




    @Override


    protected void onStop() {


        super.onStop();




    }






    // validating email id




    private boolean isValidEmail(String email) {


        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"


                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";




        Pattern pattern = Pattern.compile(EMAIL_PATTERN);


        Matcher matcher = pattern.matcher(email);


        return matcher.matches();


    }
    public void ClickMenu(View view ){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){HomePage.redirectActivity( this, Login.class);}
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

