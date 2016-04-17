package com.irecommend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.net.URI;
import org.json.JSONObject;
public class Recommendation extends AppCompatActivity {
    String uri = null;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        extras = getIntent().getExtras();
        if (extras != null) {
            uri = extras.getString("url");
        }
        Call call = new Call();
        call.execute(uri);
    }
}
class Call extends AsyncTask<String, String, String> {
    //URI url = URI.create(uri);
    JSONObject jsonobj;
    protected String doInBackground(String... arg0) {
        jsonobj = RestParser.getResponseForUrl(arg0[0], "GET");
        System.out.println(jsonobj);
        return "Success";
    }
}

