package sample.openweather.androidweather;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LocationActivity extends AppCompatActivity {
	Spinner mySpinner;
	String location="";
	FloatingActionButton button;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		mySpinner=(Spinner) findViewById(R.id.spinner1);
		adapter= new ArrayAdapter<String>(this, R.layout.spinner_design,getResources().getStringArray(R.array.spinnerItems));
		mySpinner.setAdapter(adapter);
		button= (FloatingActionButton)findViewById(R.id.fab);
		// GETTING LOCATION FROM USER USING SPINNER AND PASSING TO 
		// ANOTHER ACTIVITY USING INTENT
		
		button.setOnClickListener(new OnClickListener() {
			
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
