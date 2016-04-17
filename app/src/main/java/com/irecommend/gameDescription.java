package com.irecommend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.InterfaceAddress;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class gameDescription extends AppCompatActivity implements  doEndProcess{

    GameResult g = new GameResult();
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        extras = getIntent().getExtras();
        String gameName = null;
        if(extras != null){
            gameName = extras.getString("recName");
            gameName = gameName.replaceAll(" ", "+");
        }

        String url ="https://www.tastekid.com/api/similar?q="+gameName+"&k=219989-TasteKid-OY2OPP1B+&type=books&limit=10&info=1";

        g.response = this;
        g.execute(url);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_description);
    }
    public void getDetails(JSONObject jsonObject){
        try{
            JSONObject similarObject = jsonObject.getJSONObject("Similar");
            JSONArray infoObject = similarObject.getJSONArray("Info");
            JSONObject gameObject = infoObject.optJSONObject(0);
            String Name = gameObject.getString("Name");
            String Description = gameObject.getString("wTeaser");
            String WikiLink = gameObject.getString("wUrl");
            String YoutubeLink = gameObject.getString("yUrl");
            System.out.println(gameObject);
            System.out.println("Writing on the android application window ");
            gameName(Name);
            gameDesc(Description);
            gameWiki(WikiLink);
            gameYtube(YoutubeLink);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void gameName(String name){
        TextView Gamename = (TextView) findViewById(R.id.titleView);
        Gamename.setText("Game Name:      "+name);
    }

    public void gameDesc(String Description){
        TextView content = (TextView) findViewById(R.id.shortDescriptionView);
        content.setText("Description:     "+Description);
    }
    public void gameWiki(String WikiLink){
        TextView GamewikiLink = (TextView) findViewById(R.id.wikiView);
        GamewikiLink.setText("Wiki Link:      "+WikiLink);
    }

    public void gameYtube(String YoutubeLink){
        TextView GameYouTube = (TextView) findViewById(R.id.youtubeView);
        GameYouTube.setText("Youtube Link:     "+YoutubeLink);
    }
}

class GameResult extends AsyncTask<String,String,String> {
    JSONObject jobj;
    public doEndProcess response = null;

    @Override
    protected String doInBackground(String... arg0){
        jobj = RestParser.getResponseForUrl(arg0[0], "GET");
        return "Success!!";
    }

    protected void onPostExecute(String result){
        try {
            if("Success!!".equals(result)){
                response.getDetails(jobj);
            }
        }
        catch(Exception e){

        }

    }
}

interface doEndProcess {
    public void getDetails(JSONObject jobj);
}