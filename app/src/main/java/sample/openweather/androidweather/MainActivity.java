package sample.openweather.androidweather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get JSON data


    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_LOC = "selectloc";
    private static final String TAG_MAIN = "main";
    private static final String TAG_DES = "desc";
    private static final String TAG_HUM = "humidity";
    private static final String TAG_PRS = "pressure";
    private static final String TAG_TS = "timestamp";
    private static final String APPID = BuildConfig.APPID;/* Please generate Id from openweatherapi and paste it in gadle.properties */


    // Hashmap for ListView and temp list
    ArrayList<HashMap<String, String>> forecastList;
    HashMap<String, String> tempforecast;

    String url1 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String url2 = "&units=metric&cnt=7&appid=" + APPID;
    String urllocation;
    String url = "";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        urllocation = i.getStringExtra("mylocation");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(urllocation);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // getting location from user
        url = url1 + urllocation + url2; // adding location to the url
        Log.d("myinfo", "> " + url);
        forecastList = new ArrayList<HashMap<String, String>>();

        lv = (ListView) findViewById(R.id.list);


        // Listview on item click listener for single day details
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String main = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String date = ((TextView) view.findViewById(R.id.date))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.des))
                        .getText().toString();
                String humidity = ((TextView) view.findViewById(R.id.hum))
                        .getText().toString();
                String pressure = ((TextView) view.findViewById(R.id.prss))
                        .getText().toString();


                // Starting single day activity
                Intent in = new Intent(getApplicationContext(),
                        SingleDayActivity.class);
                in.putExtra(TAG_TS, date);
                in.putExtra(TAG_MAIN, main);
                in.putExtra(TAG_DES, description);
                in.putExtra(TAG_HUM, humidity);
                in.putExtra(TAG_PRS, pressure);
                in.putExtra("urlloc", urllocation);

                startActivity(in);

            }
        });
        // Calling async task to get json
        new GetReport().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetReport extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            RequestHandler sh = new RequestHandler();

            // Making a request to url and getting response using GET method
            String jsonStr = sh.makeServiceCall(url);


            //Log.d("myinfo", "> " + jsonStr);

            //
            //
            // EXTRACTING INFORMATION FROM JSON DATA
            // USING LOOPS FOR ARRAY AND OBJECT EXTRACTION
            // GETTING STRING FROM OBJECTS AND STRINGS
            //
            //

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray lArr = jsonObj.optJSONArray("list");

                    for (int i = 0; i < lArr.length(); i++) {

                        JSONObject c = lArr.getJSONObject(i);
                        JSONArray WArr = c.optJSONArray("weather");
                        String timestamp = getDateCurrentTimeZone(Long.parseLong(c.getString("dt")));
                        String humidity = c.getString("humidity");
                        String pressure = c.getString("pressure");

                        for (int j = 0; j < WArr.length(); j++) {
                            JSONObject c1 = WArr.getJSONObject(j);
                            String id = c1.getString("id");
                            String main = c1.getString("main");
                            String description = c1.getString("description");

                            tempforecast = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            tempforecast.put(TAG_ID, id);
                            tempforecast.put(TAG_MAIN, "Weather:" + " " + main);
                            tempforecast.put(TAG_DES, description);
                            tempforecast.put(TAG_HUM, humidity);
                            tempforecast.put(TAG_TS, timestamp);
                            tempforecast.put(TAG_PRS, pressure);
                            Log.d("myinfo", "> " + "try block");

                            // adding contact to contact list
                            forecastList.add(tempforecast);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("myinfo", "> " + e);
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        public String getDateCurrentTimeZone(long timestamp) {
            try {
                long dv = Long.valueOf(timestamp) * 1000;// its need to be in milisecond
                java.util.Date df = new java.util.Date(dv);
                String vv = new SimpleDateFormat("dd.MM.yyyy").format(df);
                return vv;
            } catch (Exception e) {
            }
            return "hello";
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView USING ADAPTER
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, forecastList,
                    R.layout.list_item, new String[]{TAG_MAIN,
                    TAG_TS, TAG_DES, TAG_HUM, TAG_PRS}, new int[]{R.id.name,
                    R.id.date, R.id.des, R.id.hum, R.id.prss});

            lv.setAdapter(adapter);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.gitIcon:
                Uri uri = Uri.parse("http://www.opensourceandroid.in");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
