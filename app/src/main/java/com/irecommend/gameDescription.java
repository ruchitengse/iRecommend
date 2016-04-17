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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_description);
        String url ="https://www.tastekid.com/api/similar?q=counter+strike&k=219989-TasteKid-OY2OPP1B+&type=books&limit=10&info=1";

        //String url = "https://videogamesrating.p.mashape.com/get.php";
        //String headerName = "X-MaShape-Key";
        //String apiKey = "rpoaoSroz8mshmXFqLV6GZ8KvhBgp1qqQGWjsnrscqtTl1HVgI";
        String gameName ="Counter-Strike";
        //String urlFinal=url+"?count=5&game="+gameName+"&"+headerName+"="+apiKey;
        //GameResult g = new GameResult();
        g.response = this;
        //g.setGameName(gameName);
        g.execute(url);
        //g.execute(urlFinal);
    }
   /* private void setTitle(String passTitle){
        TextView title = (TextView)findViewById(R.id.titleView);
        title.setText(passTitle);
    }
    private void setScore(String passScore){
        TextView score = (TextView)findViewById(R.id.scoreView);
        score.setText(passScore);
    }
    private void setShortDescription(String passDescription){
        TextView description = (TextView)findViewById(R.id.shortDescriptionView);
        description.setText(passDescription);
    }
    private void setPlatforms(String passPlatforms){
        TextView platforms = (TextView)findViewById(R.id.platformsView);
        platforms.setText(passPlatforms);
    }
    private void setThumb(String passThumb){
        TextView thumb = (TextView)findViewById(R.id.thumbView);
        thumb.setText(passThumb);
    }
*/
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

            //setTitle(jsonObject.getString("title"));
            //setScore(jsonObject.getString("score"));
            //setShortDescription(jsonObject.getString("short_description"));

            //JSONObject jsonPlatforms = new JSONObject(jsonObject.getString("platforms"));
//            int count2 = jsonPlatforms.length();
//            for(int j =0;j<count2;j++) {
//                setPlatforms(jsonPlatforms.getString("platforms"));
//            }
            //Iterator<String> keys = jsonPlatforms.keys();
           // String key;
            //String value;
            //String platformNames="";
            //while(keys.hasNext()){
              //  key = (String)keys.next();
               // value = jsonPlatforms.getString(key);
               // if(!"".equals(platformNames)){
                 //   platformNames += ",";
                //}
                //platformNames+=value;
           // }
           // setPlatforms(platformNames);
           // setThumb(jsonObject.getString("thumb"));
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
    //JSONArray jsonArray;
    //String gameName = "";
    //doEndProcess p = null;

  /*  public void setGameName(String gameName){
        this.gameName = gameName;
    }
*/
    @Override
    protected String doInBackground(String... arg0){

       // HashMap<String,String> headers = new HashMap<String,String>();
        //headers.put("X-MaShape-Key","rpoaoSroz8mshmXFqLV6GZ8KvhBgp1qqQGWjsnrscqtTl1HVgI");
        //headers.put("Accept","application/json");
        //jsonArray = RestParser.getResponseForUrlWithHeaders(urlFinal[0], "GET",headers);
        jobj = RestParser.getResponseForUrl(arg0[0], "GET");
        //System.out.println(jsonArray);
        System.out.println(arg0[0]);
        return "Success!!";
    }

    protected void onPostExecute(String result){
        //int count = jobj.length();
        // int count = jsonArray.length();
        try {

            //for (int i = 0; i < count; i++) {
                //JSONObject jsobObj = jobj.optJSONObject(i);
                // JSONObject jsonObj = jsonArray.optJSONObject(i);
            if("Success!!".equals(result)){
                response.getDetails(jobj);
            }

            /*if (jobj.getString("title").equalsIgnoreCase(this.gameName)) {
                    System.out.println("Item:"+jobj);
                    p.getDetails(jobj);
                    break;
                }
                //System.out.println(jsonObj.getString("title"));
            }
*/
        }
        catch(Exception e){

        }

    }
/*
    @Override
    protected void onProgressUpdate(String... something){
    }
    */
}

interface doEndProcess {
    public void getDetails(JSONObject jobj);
}