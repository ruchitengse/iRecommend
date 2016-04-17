package com.irecommend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class TvSeries extends AppCompatActivity implements FetchTvData {

    FetchTvSeries tvSeries = new FetchTvSeries();
    String url = "http://www.omdbapi.com/?y=&plot=short&r=json&type=series";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series);
        String seriesName = "friends";
        url += "&t="+seriesName;
        tvSeries.delegate = this;
        tvSeries.execute(url);
    }

    @Override
    public void processFinish(JSONObject jsonObject){
        try {
            setTitle(jsonObject.getString("Title"));
            setPlot(jsonObject.getString("Plot"));
            setActors(jsonObject.getString("Actors"));
            setGenre(jsonObject.getString("Genre"));
            setImdbRating(jsonObject.getString("imdbRating"));
            setLanguage(jsonObject.getString("Language"));
            setReleased(jsonObject.getString("Released"));
            setRated(jsonObject.getString("Rated"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setRated(String rated){
        TextView tvSeriesRated = (TextView) findViewById(R.id.tvSeriesRated);
        tvSeriesRated.setText("Rated: " + rated);
    }

    private void setLanguage(String language){
        TextView tvSeriesLanguage = (TextView) findViewById(R.id.tvSeriesLanguage);
        tvSeriesLanguage.setText("Language: " + language);
    }

    private void setPlot(String plot){
        TextView tvSeriesPlot = (TextView) findViewById(R.id.tvSeriesPlot);
        tvSeriesPlot.setText("Plot: " + plot);
    }

    private void setImdbRating(String imdbRating) {
        TextView imdbRatingView = (TextView)  findViewById(R.id.tvSeriesRating);
        imdbRatingView.setText(imdbRating);
    }

    private void setActors(String actors) {
        TextView tvSeriesStarring = (TextView) findViewById(R.id.tvSeriesStarring);
        tvSeriesStarring.setText("Starring: "+ actors);
    }

    private void setGenre(String genre) {
        TextView tvSeriesGenre = (TextView) findViewById(R.id.tvSeriesGenre);
        tvSeriesGenre.setText("Genre: "+  genre);
    }

    private void setReleased(String released) {
        TextView tvSeriesReleased = (TextView) findViewById(R.id.tvSeriesReleased);
        tvSeriesReleased.setText("Year:" + released);
    }

    public void setTitle(String tvSeriesTitle){
        TextView filmTitle = (TextView) findViewById(R.id.tvSeriesTitle);
        filmTitle.setText(tvSeriesTitle);
    }
}

class FetchTvSeries extends AsyncTask<String, String, String>{

    JSONObject jsonobj;
    public FetchTvData delegate = null;

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

interface FetchTvData{
    public void processFinish(JSONObject jsonObject);
}