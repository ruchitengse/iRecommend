package com.irecommend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_description);

        extras = getIntent().getExtras();
        String movieName = null;
        if(extras != null){
            movieName = extras.getString("recName");
        }

        movieData.delegate = this;
        movieData.execute("http://www.omdbapi.com/?t="+movieName+"&y=&plot=short&r=json");
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
            setLanguage(jsonObject.getString("Language"));

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
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
        imdbRatingView.setText(imdbRating);
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
        Bitmap mIcon11 = null;
        ImageView imageView = (ImageView) findViewById(R.id.movieImg);
        try {
            InputStream in = (InputStream) new java.net.URL("http://ia.media-imdb.com/images/M/MV5BMTMyNjk4Njc3NV5BMl5BanBnXkFtZTcwNDkyMTEzMw@@._V1_SX300.jpg").getContent();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mIcon11 != null){
            imageView.setImageBitmap(mIcon11);
        } else {
            Toast.makeText(MovieDescription.this, "Image does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    
}

/**
 * {"Title":"The Devil Wears Prada",
 "Year":"2006",
 "Rated":"PG-13",
 "Released":"30 Jun 2006",
 "Runtime":"109 min",
 "Genre":"Comedy, Drama, Romance",
 "Director":"David Frankel",
 "Writer":"Aline Brosh McKenna (screenplay), Lauren Weisberger (novel)",
 "Actors":"Meryl Streep, Anne Hathaway, Emily Blunt, Stanley Tucci",
 "Plot":"A smart but sensible new graduate lands a job as an assistant to Miranda Priestly, the demanding editor-in-chief of a high fashion magazine.",
 "Language":"English, French",
 "Country":"USA, France",
 "Awards":"Nominated for 2 Oscars. Another 16 wins & 48 nominations.",
 "Poster":"http://ia.media-imdb.com/images/M/MV5BMTMyNjk4Njc3NV5BMl5BanBnXkFtZTcwNDkyMTEzMw@@._V1_SX300.jpg",
 "Metascore":"62",
 "imdbRating":"6.8",
 "imdbVotes":"275,
 709",
 "imdbID":"tt0458352",
 "Type":"movie",
 "Response":"True"}
 */
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