package com.irecommend;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Ruchi.U on 4/16/2016.
 */
public abstract class RestParser {

    public JSONObject getResponseForUrl(String url, String requestMethod) {

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL urlToOpen = new URL(url);
            urlConnection = (HttpURLConnection) urlToOpen.openConnection();

            /** Set Request Properties **/
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Accept","application/json");

            /** Set Method **/
            urlConnection.setRequestMethod(requestMethod);
            int statusCode = urlConnection.getResponseCode();

            if(statusCode == 200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                return new JSONObject(getResponseText(inputStream));
            }


        } catch (Exception e){
            Log.d("getResponseForUrl", e.getLocalizedMessage());
        }
        return  null;
    }

    private String getResponseText(InputStream inputStream){
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
}
