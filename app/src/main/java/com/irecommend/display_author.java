package com.irecommend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

public class display_author extends AppCompatActivity implements authorResponse{
    AuthorInfo author = new AuthorInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_author);

        String ApiCall = "https://www.tastekid.com/api/similar?q=sidney+sheldon&k=219989-TasteKid-OY2OPP1B&type=author&limit=10&info=1";

        author.response=this;
        author.execute(ApiCall);
    }

    @Override
    public void processFinish(JSONObject jsonObject)  {
        try {
            JSONObject similarObject = jsonObject.getJSONObject("Similar");
            JSONArray infoObject = similarObject.getJSONArray("Info");
            JSONObject authObject = infoObject.optJSONObject(0);
            String Name = authObject.getString("Name");
            String auth_introduction = authObject.getString("wTeaser");
            System.out.println(authObject);
            System.out.println("Writing on the android application window ");
            authName(Name);
            authInto(auth_introduction);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void authName(String name){
        TextView authName = (TextView) findViewById(R.id.authName);
        authName.setText("Author Name:      "+name);
    }

    public void authInto(String authIntro){
        TextView introduction = (TextView) findViewById(R.id.authIntro);
        introduction.setText("Author Information:     "+authIntro);
    }


}


class AuthorInfo extends AsyncTask<String, String, String> {
    JSONObject jobj;
    public authorResponse response = null;

    @Override
    protected String doInBackground(String... arg0) {
        jobj = RestParser.getResponseForUrl(arg0[0], "GET");
        System.out.println(jobj);
        return "Success!!!";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if ("Success!!!".equals(result)) {
                response.processFinish(jobj);
            }
        } catch (Exception e) {

        }

    }

}


interface authorResponse {
    public void processFinish(JSONObject output) ;
}

