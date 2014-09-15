package com.dise.weekone.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.dise.weekone.R;
import com.dise.weekone.mapUtils.SubsamplingScaleImageView;
import com.dise.weekone.util.Const;

public class MapView extends Activity {

	protected ActionBar ab;
	protected Bundle b;
	SubsamplingScaleImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_university_park);

		Intent intent = getIntent();
		b = intent.getExtras();

		ab = getActionBar();
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(b.getString(Const.CAMPUS_NAME));

		Typeface fontAb = Typeface.createFromAsset(getAssets(),
				"Montserrat-Regular.ttf");

		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
				title.setTypeface(fontAb);
				title.setTextSize(16);

			}

			imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);

			String name = b.getString(Const.MAP_NAME);
			imageView.setImageAsset(name);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			onBackPressed();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

}
