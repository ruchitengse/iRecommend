package com.irecommend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.net.URI;

public class Recommendation extends AppCompatActivity {
    String uri=null;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        extras = getIntent().getExtras();
        if (extras != null) {
           uri = extras.getString("url");
        }

        URI url = URI.create(uri);

    }
}
