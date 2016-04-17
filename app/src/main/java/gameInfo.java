import android.os.AsyncTask;

import com.irecommend.RestParser;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by meena on 4/16/2016.
 */
public class gameInfo {

    String url = "https://videogamesrating.p.mashape.com/get.php";
    String headerName = "X-MaShape-Key";
    String apiKey = "rpoaoSroz8mshmXFqLV6GZ8KvhBgp1qqQGWjsnrscqtTl1HVgI";
    //String gameName ="counter strike";

    public void getGameDespcription(String game_name){
        String urlFinal = url+"?count=5&game="+game_name+"&"+headerName+"="+apiKey;
        System.out.println(urlFinal);
        new GameResult().execute(urlFinal);

    }
    public static void main(String args[]){

        new gameInfo().getGameDespcription("counter strike");
    }
}

class GameResult extends AsyncTask<String,String,String>{
    JSONObject jsonobj;

    @Override
    protected String doInBackground(String... urlFinal){
        jsonobj = RestParser.getResponseForUrl(urlFinal[0], "GET");
        System.out.println(jsonobj);
        return null;
    }

    protected void onPostExecute(String somethingelse){

    }

    @Override
    protected void onProgressUpdate(String... something){

    }
}
