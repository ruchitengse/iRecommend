package com.irecommend;

import android.app.Activity;
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

public class DisplayBooks extends Activity implements bookResponse{


    Bundle extras;
    BooksInfo books = new BooksInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        extras = getIntent().getExtras();
        String bookName = null;
        if(extras != null){
            bookName = extras.getString("recName");
            bookName = bookName.replaceAll(" ", "+");
        }
        String ApiCall = "https://www.tastekid.com/api/similar?q="+bookName+"&k=219989-TasteKid-OY2OPP1B&type=books&limit=10&info=1";

        books.response=this;
        books.execute(ApiCall);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_books);

    }

    @Override
    public void processFinish(JSONObject jsonObject)  {
        try {
            JSONObject similarObject = jsonObject.getJSONObject("Similar");
            JSONArray infoObject = similarObject.getJSONArray("Info");
            JSONObject bookObject = infoObject.optJSONObject(0);
            String Name = bookObject.getString("Name");
            String Description = bookObject.getString("wTeaser");
            System.out.println(bookObject);
            System.out.println("Writing on the android application window ");
            bookName(Name);
            bookDesc(Description);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void bookName(String name){
        TextView bookName = (TextView) findViewById(R.id.bookName);
        bookName.setText("Book Name:      "+name);
    }

    public void bookDesc(String bookContent){
        TextView content = (TextView) findViewById(R.id.bookContent);
        content.setText("Description:     "+bookContent);
    }

}


 class BooksInfo extends AsyncTask<String, String, String> {
     JSONObject jobj;
     public bookResponse response = null;

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


   interface bookResponse {
         public void processFinish(JSONObject output) ;
     }



