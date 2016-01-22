package com.perseus.fblogin;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;

import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public String text="";
    public Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("user_posts","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
    /*            info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

    */

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    String k = "";
                                    System.out.println(object.toString());
                                    JSONObject json = object.getJSONObject("feed");

                                    JSONArray json1 = json.getJSONArray("data");
                                    for (int i = 0; i < json1.length(); i++) {
                                        JSONObject j = json1.getJSONObject(i);
                                        try {
                                            k += j.get("message").toString();
                                            k += " ";
                                            k += j.get("description").toString();
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                        k+=" ";

                                    }
                                    text = k;
  //                                  info.setText(k);
                                    System.out.println(json1.toString());
                                    System.out.println(k);
                                }
                                    catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                new Data().execute();

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "feed");
                request.setParameters(parameters);
                request.executeAsync();
            }
            class Data extends AsyncTask<String,String,String> {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if(text=="")
                        Toast.makeText(MainActivity.this, "Please make your data public" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                    intent.putExtra("fb",profile.toString());
                    startActivity(intent);

//                    info.setText(profile.toString());
                }

                protected String doInBackground(String... args) {

                    if (text!="" && text.length()>100) {
                        PersonalityInsights service = new PersonalityInsights();
                        service.setUsernameAndPassword("7da47cd4-8fc5-4b12-a808-6b01a05d44b5", "6hrtYIaw7MQp");
                        profile = service.getProfile(text);

                        //Toast.makeText(MainActivity.this, profile.toString().substring(0, 13) + "...", Toast.LENGTH_SHORT).show();

                    }


                    return null;

                }
            }
            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.perseus.fblogin/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.perseus.fblogin/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
