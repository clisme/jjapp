package com.example.testimage;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class BackUI extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.back_layout);
		// hui
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
