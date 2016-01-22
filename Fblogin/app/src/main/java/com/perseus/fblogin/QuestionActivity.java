package com.perseus.fblogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    public String text,text1="";
    EditText r1;
    TextView q1;
    Button Next;
    int i=0;
    Profile profile_hr;
    String profile_fb;
    List ques=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        profile_fb = intent.getStringExtra("fb");

        ques.add("Why should i hire you?");
        ques.add("what are your strengths and weaknesses?");
        ques.add("why do you want to work at our company?");
        ques.add("what is difference between confidence and over-confidence?");
        ques.add("what is difference between hardwork and smartwork?");
        ques.add("How do you feel about working at nights and weekends?");
        ques.add("Can you work under pressure?");
        ques.add("Are you willing to relocate or travel?");
        ques.add("What are your goals?");
        ques.add("What motivates you to do a good job?");
        ques.add("What makes you angry?");
        ques.add("Give me an example of your creativity?");
        ques.add("How long you would you expect to work for us if hired?");
        ques.add("Are you not overqualified for this position?");


        q1=(TextView)findViewById(R.id.Q1);
        r1=(EditText)findViewById(R.id.R1);
        Next=(Button)findViewById(R.id.button);
        text="Give atleast 100 words";
    }
    public void isNext(View v){
        i+=1;
        if(i<=ques.size()) {
            text1 += " " + r1.getText();
            q1.setText(String.format("%s", ques.get(i - 1)));
            r1.setText("");
        }
        else {
            View b = findViewById(R.id.button);
            b.setVisibility(View.GONE);
            q1.setText("Thank you.Please proceed to Tech Round");
            //r1.setVisibility(View.INVISIBLE);
            new Data().execute();
        }
    }

    class Data extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(QuestionActivity.this, TechActivity.class);
            intent.putExtra("hr",profile_hr.toString());
            intent.putExtra("fb",profile_fb);
            startActivity(intent);
        }

        protected String doInBackground(String... args) {
            if(text1!="" && text1.length()>100) {
                PersonalityInsights service = new PersonalityInsights();

                service.setUsernameAndPassword("7da47cd4-8fc5-4b12-a808-6b01a05d44b5", "6hrtYIaw7MQp");
                profile_hr = service.getProfile(text1);
                System.out.println(profile_hr);
            }
            //Toast.makeText(MainActivity.this, profile.toString().substring(0, 13) + "...", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
