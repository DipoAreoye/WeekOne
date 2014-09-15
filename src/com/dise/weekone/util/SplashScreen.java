package com.dise.weekone.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dise.weekone.R;
import com.dise.weekone.ui.MainActivity;

public class SplashScreen extends Activity {



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_splash);

		
		Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
		SplashScreen.this.startActivity(mainIntent);
		SplashScreen.this.finish();

	}

	
}