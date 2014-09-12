package com.dise.weekone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.dise.weekone.Twitter.Authenticated;
import com.dise.weekone.Twitter.Tweet;
import com.dise.weekone.Twitter.Twitter;
import com.dise.weekone.adapters.FeedAdapter;
import com.dise.weekone.util.BaseFragment;
import com.dise.weekone.util.Const;
import com.google.gson.Gson;

public class FeedFragment extends BaseFragment implements OnScrollListener {

	protected final static String ScreenName = Const.UON_TWITTER_NAME;
	protected Twitter twits;
	protected DownloadTwitterTask downloadTwitterTask;
	protected int tweetCount = 10;
	protected FeedAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_feed, container,
				false);

		ab.setTitle("UoN Freshers");

		downloadTweets();

		return rootView;

	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (downloadTwitterTask != null)
			downloadTwitterTask.cancel(true);
	}

	// download twitter timeline after first checking to see if there is a
	// network connection
	public void downloadTweets() {
		ConnectivityManager connMgr = (ConnectivityManager) mainActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {

			downloadTwitterTask = new DownloadTwitterTask();
			downloadTwitterTask.execute(ScreenName);
		} else {
			Log.v(null, "No network connection available.");
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {	
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (getListView().getLastVisiblePosition() == (adapter.getCount() - 1))

			if (FeedAdapter.REACHED_THE_END) {
				Log.v(null, "Loading more data");
				FeedAdapter.REACHED_THE_END = false;
				tweetCount += 10;
				downloadTweets();

			}
	}

	public void adaptData() {

		if (getListView().getAdapter() == null) {
			adapter = new FeedAdapter(getActivity(), twits);
			getListView().setOnScrollListener(this);
			getListView().setAdapter(adapter);
		} else {
			Log.i(null, "Count: " + twits.size());
			((FeedAdapter) getListView().getAdapter()).refillTweets(twits);
		}

	}

	// Uses an AsyncTask to download a Twitter user's timeline
	private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
		final static String CONSUMER_KEY = "B4VPpnbx7Uz7S61XNsS316qxC";
		final static String CONSUMER_SECRET = "skIOIJMZLrkBfJKIDL4CR6PjyoTYAHMEBb7KolzbszQ7FtBHcR";
		final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
		final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

		@Override
		protected String doInBackground(String... screenNames) {
			String result = null;

			if (screenNames.length > 0) {
				result = getTwitterStream(screenNames[0]);
			}
			return result;
		}

		// onPostExecute convert the JSON results into a Twitter object (which
		// is an Array list of tweets
		@Override
		protected void onPostExecute(String result) {

			twits = jsonToTwitter(result);

			// lets write the results to the console as well
			for (Tweet tweet : twits) {
				// Log.i(null, tweet.getText());
			}

			adaptData();

		}

		// converts a string of JSON data into a Twitter object
		private Twitter jsonToTwitter(String result) {
			Twitter twits = null;
			if (result != null && result.length() > 0) {
				try {
					Gson gson = new Gson();
					twits = gson.fromJson(result, Twitter.class);
				} catch (IllegalStateException ex) {
					// just eat the exception
				}
			}
			return twits;
		}

		// convert a JSON authentication object into an Authenticated object
		private Authenticated jsonToAuthenticated(String rawAuthorization) {
			Authenticated auth = null;
			if (rawAuthorization != null && rawAuthorization.length() > 0) {
				try {
					Gson gson = new Gson();
					auth = gson.fromJson(rawAuthorization, Authenticated.class);
				} catch (IllegalStateException ex) {
					// just eat the exception
				}
			}
			return auth;
		}

		private String getResponseBody(HttpRequestBase request) {
			StringBuilder sb = new StringBuilder();
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient(
						new BasicHttpParams());
				HttpResponse response = httpClient.execute(request);
				int statusCode = response.getStatusLine().getStatusCode();
				String reason = response.getStatusLine().getReasonPhrase();

				if (statusCode == 200) {

					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();

					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(inputStream, "UTF-8"), 8);
					String line = null;
					while ((line = bReader.readLine()) != null) {
						sb.append(line);
					}
				} else {
					sb.append(reason);
				}
			} catch (UnsupportedEncodingException ex) {
			} catch (ClientProtocolException ex1) {
			} catch (IOException ex2) {
			}
			return sb.toString();
		}

		private String getTwitterStream(String screenName) {
			String results = null;

			// Step 1: Encode consumer key and secret
			try {
				// URL encode the consumer key and secret
				String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
				String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET,
						"UTF-8");

				// Concatenate the encoded consumer key, a colon character, and
				// the
				// encoded consumer secret
				String combined = urlApiKey + ":" + urlApiSecret;

				// Base64 encode the string
				String base64Encoded = Base64.encodeToString(
						combined.getBytes(), Base64.NO_WRAP);

				// Step 2: Obtain a bearer token
				HttpPost httpPost = new HttpPost(TwitterTokenURL);
				httpPost.setHeader("Authorization", "Basic " + base64Encoded);
				httpPost.setHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				httpPost.setEntity(new StringEntity(
						"grant_type=client_credentials"));
				String rawAuthorization = getResponseBody(httpPost);
				Authenticated auth = jsonToAuthenticated(rawAuthorization);

				// Applications should verify that the value associated with the
				// token_type key of the returned object is bearer
				if (auth != null && auth.token_type.equals("bearer")) {

					// Step 3: Authenticate API requests with bearer token
					HttpGet httpGet = new HttpGet(TwitterStreamURL + screenName
							+ "&count=" + tweetCount);

					// construct a normal HTTPS request and include an
					// Authorization
					// header with the value of Bearer <>
					httpGet.setHeader("Authorization", "Bearer "
							+ auth.access_token);
					httpGet.setHeader("Content-Type", "application/json");
					// update the results with the body of the response
					results = getResponseBody(httpGet);
				}
			} catch (UnsupportedEncodingException ex) {
			} catch (IllegalStateException ex1) {
			}
			return results;
		}
	}

}