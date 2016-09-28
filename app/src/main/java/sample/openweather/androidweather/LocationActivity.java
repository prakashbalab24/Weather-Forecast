package sample.openweather.androidweather;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class LocationActivity extends Activity {
	//private static final String TAG_LOC = "selectloc";
	Spinner mySpinner;
	String location="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		mySpinner=(Spinner) findViewById(R.id.spinner1);
		Button b1 = (Button)findViewById(R.id.button1);
		
		// GETTING LOCATION FROM USER USING SPINNER AND PASSING TO 
		// ANOTHER ACTIVITY USING INTENT
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				location =mySpinner.getSelectedItem().toString();
				Intent intent = new Intent(LocationActivity.this,MainActivity.class);
				
				intent.putExtra("mylocation", location);
				startActivity(intent);
				
			}
		});
		
	}


}
