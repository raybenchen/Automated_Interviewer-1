package com.perseus.fblogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TextView;

public class FinalActivity extends ActionBarActivity {
    EditText r1,r2,r3;
    TextView q1,q2,q3;
    String profile_fb,profile_hr;
    int tch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Intent intent = getIntent();
        profile_fb = intent.getStringExtra("fb");
        profile_hr = intent.getStringExtra("hr");
        tch = intent.getIntExtra("tch",56);
        q1=(TextView)findViewById(R.id.Q1);
        r1=(EditText)findViewById(R.id.R1);
        r1.setText(profile_fb);
        q2=(TextView)findViewById(R.id.Q2);
        r2=(EditText)findViewById(R.id.R2);
        r2.setText(profile_hr);
        q3=(TextView)findViewById(R.id.Q3);
        r3=(EditText)findViewById(R.id.R3);
        r3.setText(String.valueOf(tch));
    }

}
