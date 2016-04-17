package com.irecommend;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
public class Recommendation extends AppCompatActivity {
    String uri = null;
    String type = null;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        extras = getIntent().getExtras();
        if (extras != null) {
            uri = extras.getString("url");
            type = extras.getString("type");
        }
        Call call = new Call();
        call.execute(uri, type);
    }

    class Call extends AsyncTask<String, String, JSONObject> {
        //URI url = URI.create(uri);
        JSONObject jsonobj;
        String[] lm;
        String type;
        String recName = null;
        @Override
        protected JSONObject doInBackground(String... arg0) {

            //System.out.println(jsonobj);
            jsonobj = RestParser.getResponseForUrl(arg0[0], "GET");
            setType(arg0[1]);

            return jsonobj;
        }

        public void setType(String type){
            this.type = type;
        }

        @Override
        protected void onPostExecute(JSONObject result){

            lm = JsonParser(result);
            ListView r_listView = (ListView) findViewById(R.id.listView);
            ArrayAdapter adapter = new ArrayAdapter(Recommendation.this,android.R.layout.simple_list_item_1,lm);
            r_listView.setAdapter(adapter);
            r_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    //Toast.makeText(Recommendation.this, lm[position], Toast.LENGTH_LONG).show();
                    recName = lm[position];
                    //Change second parameter
                    if(type == "Movies") {
                        Intent intent = new Intent(Recommendation.this, MovieDescription.class);
                        intent.putExtra("recName", recName);
                        startActivity(intent);
                    } else if(type == "Shows"){
                        Intent intent = new Intent(Recommendation.this, TvSeries.class);
                        intent.putExtra("recName", recName);
                        startActivity(intent);
                    } else if(type == "Books"){
                        Intent intent = new Intent(Recommendation.this, MovieDescription.class);
                        intent.putExtra("recName", recName);
                        startActivity(intent);
                    } else if(type == "Authors") {
                        Intent intent = new Intent(Recommendation.this, MovieDescription.class);
                        intent.putExtra("recName", recName);
                        startActivity(intent);
                    } else if(type == "Games"){
                        Intent intent = new Intent(Recommendation.this, MovieDescription.class);
                        intent.putExtra("recName", recName);
                        startActivity(intent);
                    }

                }
            });
        }

        private String[] JsonParser(JSONObject jsonobj){
            String[] lm = new String[10];

            try{
                System.out.println("Here");
                JSONObject  jsonRootObject = jsonobj.getJSONObject("Similar");
                //JSONArray similar =
                //System.out.println(jsonRootObject);
                JSONArray arrays = jsonRootObject.getJSONArray("Results");
                lm = new String[arrays.length()];
                //System.out.println(arrays);
            /*JSONObject si = similar.getJSONObject(1);
            System.out.println(si);
            JSONArray arrays = si.getJSONArray("Results");*/
                for(int i=0;i<arrays.length();i++){
                    String name;
                    JSONObject item = arrays.getJSONObject(i);
                    name = item.getString("Name");
                    lm[i]=name;
                    System.out.println(lm[i]);
                }
                System.out.println(lm);

            }
            catch(Exception e){
                System.out.println("Catch");
            }

            return lm;
        }}}

