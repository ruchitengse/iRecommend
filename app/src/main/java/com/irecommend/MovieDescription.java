package com.irecommend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;

public class MovieDescription extends AppCompatActivity implements MovieResponse {

    MovieData movieData = new MovieData();
    String urlForDescription = "http://www.omdbapi.com/?&y=&plot=short&r=json";
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extras = getIntent().getExtras();
        String movieName = null;
        if(extras != null){
            movieName = extras.getString("recName");
            movieName = movieName.replaceAll(" ", "+");
        }
//        movieName = "Titanic";
        movieData.delegate = this;
        movieData.execute("http://www.omdbapi.com/?t="+movieName+"&y=&plot=short&r=json&type=movie");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
    }

    @Override
    public void processFinish(JSONObject jsonObject) throws  Exception {
        try {
            setTitle(jsonObject.getString("Title"));
            setImage(jsonObject.getString("Poster"));
            setReleased(jsonObject.getString("Released"));
            setGenre(jsonObject.getString("Genre"));
            setImdbRating(jsonObject.getString("imdbRating"));
            setActors(jsonObject.getString("Actors"));
            setPlot(jsonObject.getString("Plot"));
            setRated(jsonObject.getString("Rated"));
            setLanguage(jsonObject.getString("Language"));

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    private void setRated(String rated) {
        TextView movieRated = (TextView) findViewById(R.id.movieRated);
        movieRated.setText("Language: " + rated);
    }

    private void setLanguage(String language){
        TextView movieLanguage = (TextView) findViewById(R.id.movieLanguage);
        movieLanguage.setText("Language: " + language);
    }

    private void setPlot(String plot){
        TextView moviePlot = (TextView) findViewById(R.id.moviePlot);
        moviePlot.setText("Plot: " + plot);
    }

    private void setImdbRating(String imdbRating) {
        TextView imdbRatingView = (TextView)  findViewById(R.id.imdbRating);
        imdbRatingView.setText("Imdb Rating: " + imdbRating);
    }

    private void setActors(String actors) {
        TextView movieStarring = (TextView) findViewById(R.id.movieStarring);
        movieStarring.setText("Starring: "+ actors);
    }

    private void setGenre(String genre) {
        TextView movieGenre = (TextView) findViewById(R.id.movieGenre);
        movieGenre.setText("Genre: "+  genre);
    }

    private void setReleased(String released) {
        TextView movieReleased = (TextView) findViewById(R.id.movieReleased);
        movieReleased.setText("Released:" + released);
    }

    public void setTitle(String movieTitle){
        TextView filmTitle = (TextView) findViewById(R.id.movieTitle);
        filmTitle.setText(movieTitle);
    }

    public void setImage(String posterLink){

        WebView displayImage = (WebView) findViewById(R.id.imgView);
        displayImage.getSettings().setJavaScriptEnabled(true);
        displayImage.getSettings().setLoadWithOverviewMode(true);
        displayImage.getSettings().setUseWideViewPort(true);
        displayImage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        displayImage.setScrollbarFadingEnabled(true);
        displayImage.loadDataWithBaseURL("http://ia.media-imdb.com", "<img src=\""+posterLink+"\" height=\"98%\" width=\"100%\"/>", "text/html", "utf-8", null);
    }

    
}

class MovieData extends AsyncTask<String, String, String> {
    //URI url = URI.create(uri);

    JSONObject jsonobj;
    public MovieResponse delegate = null;

    @Override
    protected String doInBackground(String... arg0) {
        jsonobj = RestParser.getResponseForUrl(arg0[0], "GET");
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if ("Success".equals(result)) {
                delegate.processFinish(jsonobj);
            }
        } catch (Exception e) {

        }
    }
}

interface MovieResponse {
    public void processFinish(JSONObject output) throws  Exception;
}