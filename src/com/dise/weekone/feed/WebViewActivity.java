package com.dise.weekone.feed;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dise.weekone.R;

public class WebViewActivity extends Activity {

	protected String url;
	protected ProgressBar mProgressBar;
	protected ActionBar ab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_web_view);

		ab = getActionBar();
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		Typeface fontAb = Typeface
				.createFromAsset(getAssets(), "Montserrat-Regular.ttf");

		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
				title.setTypeface(fontAb);
				title.setTextSize(16);

			}
		}

		getActionBar().setTitle("UoN Freshers");

		Intent intent = getIntent();
		Uri webUri = intent.getData();

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

		WebView wv = (WebView) findViewById(R.id.webview);
		url = webUri.toString();

		if (!url.contains("htt")) {

			url = "http://" + url;
			Log.i(null, "new url: " + url);

		}

		wv.loadUrl(url);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				view.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.INVISIBLE);
				view.setVisibility(View.VISIBLE);
				animate(view);
				super.onPageFinished(view, url);
			}

			private void animate(final WebView view) {
				Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
						android.R.anim.fade_in);
				view.startAnimation(anim);
			}
		});

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
