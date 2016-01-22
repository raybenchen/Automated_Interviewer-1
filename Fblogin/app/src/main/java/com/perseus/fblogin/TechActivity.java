package com.perseus.fblogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TechActivity extends ActionBarActivity {
    public String text,text1="";
    EditText r1;
    TextView q1;
    Button Next;
    int i=0;
    List ques=new ArrayList();
    List ans=new ArrayList();
    String profile_fb,profile_hr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);
        Intent intent = getIntent();
        profile_fb = intent.getStringExtra("fb");
        profile_hr = intent.getStringExtra("hr");


        ques.add("What do you mean by platform independence?");
        ques.add("What is the purpose of declaring a variable as final?");
        ques.add("What is the impact of declaring a method as final?");
        ques.add("What is the difference between method overriding and overloading?");

        //ans.add("platform independent. any other. value can't change. can't , overridden, override .same name , arguments, different arguments.
                q1=(TextView)findViewById(R.id.Q1);
        r1=(EditText)findViewById(R.id.R1);
        Next=(Button)findViewById(R.id.button);

    }
    public void isNext(View v){
        i+=1;
        if(i<=ques.size()) {
            text1 += " " + r1.getText();
            q1.setText(String.format("%s", ques.get(i - 1)));
            r1.setText("");
        }
        else {
            //str1.toLowerCase().contains(str2.toLowerCase())
            View b = findViewById(R.id.button);
            b.setVisibility(View.GONE);
            q1.setText("Thank you.You may leave");
            r1.setVisibility(View.INVISIBLE);
            int score=78;
            Intent intent = new Intent(TechActivity.this, FinalActivity.class);
            intent.putExtra("fb",profile_fb.toString());
            intent.putExtra("hr",profile_hr.toString());
            intent.putExtra("tch",score);
            startActivity(intent);
        }
    }
}
