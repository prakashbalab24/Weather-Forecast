package sample.openweather.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleDayActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_ID = "id";
	private static final String TAG_LOC = "selectloc";
	private static final String TAG_MAIN = "main";
	private static final String TAG_DES = "desc";
	private static final String TAG_HUM = "humidity";
	private static final String TAG_PRS = "pressure";
	private static final String TAG_TS = "timestamp";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_day);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
      String location = in.getStringExtra("urlloc");
        String main = in.getStringExtra(TAG_MAIN);
        String description = in.getStringExtra(TAG_DES);
        String humidity = in.getStringExtra(TAG_HUM);
        String pressure = in.getStringExtra(TAG_PRS);
        String time = in.getStringExtra(TAG_TS);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.main);
        ImageView im = (ImageView) findViewById(R.id.im);
        TextView lbltime = (TextView) findViewById(R.id.date);
        TextView lblhum = (TextView) findViewById(R.id.hum);
        TextView lblprss = (TextView) findViewById(R.id.prss);
        TextView lbldes = (TextView) findViewById(R.id.des);
        TextView lblloc = (TextView) findViewById(R.id.loc);
        
        
        if(lblName.getText().toString().equals("clear")||lblName.getText().toString().equals("Clear"))
        {
        im.setVisibility(View.VISIBLE);	
        }
        lblName.setText(main);
        lblloc.setText(location);
        lbldes.setText(description);
        lblhum.setText(humidity);
        lblprss.setText(pressure);
        lbltime.setText(time);

    }
}
