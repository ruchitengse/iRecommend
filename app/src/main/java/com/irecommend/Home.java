package com.irecommend;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.net.URI;
import android.content.Intent;

public class Home extends AppCompatActivity {

    private EditText searchItem;
    private RadioButton r_button;
    private RadioGroup r_selectedCategory;
    String category=null, searchData =null, url =null;
    Button search;
    //URL url1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addListenerOnButton();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void addListenerOnButton() {
        searchItem = (EditText) findViewById(R.id.searchItem);
        r_selectedCategory = (RadioGroup) findViewById(R.id.searchCategory);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = r_selectedCategory.getCheckedRadioButtonId();
                r_button = (RadioButton) findViewById(selectedId);
                category = r_button.getText().toString();
                searchData = searchItem.getText().toString();
                searchData = searchData.replaceAll(" ","+");
                url = "https://www.tastekid.com/api/similar?q=" + searchData + "&k=219989-TasteKid-OY2OPP1B" + "&type=" + category+"&limit=10&info=1";


               // URL url1 = new URL(url);
                //URI uri = URI.create(url);
                //Toast.makeText(Home.this,
                  //      url, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Recommendation.class);
                intent.putExtra("url",url);
                intent.putExtra("type",category);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.irecommend/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.irecommend/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
