package com.dise.weekone;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dise.weekone.adapters.EndlessScrollListener;
import com.dise.weekone.adapters.FeedAdapter;
import com.dise.weekone.util.BaseFragment;
import com.dise.weekone.util.Const;

public class NewFeedFragment extends BaseFragment {

	protected final static String ScreenName = Const.UON_TWITTER_NAME;
	// protected Twitter twits;
	// protected DownloadTwitterTask downloadTwitterTask;
	Paging pager = new Paging(1, 10);
	int pagecount = 1;
	protected FeedAdapter adapter;
	protected ProgressBar progressBar;
	protected List<Status> statuses;
	protected SwipeRefreshLayout swipeLayout;
	protected Typeface defaultFont;
	protected DownloadTwitterTask downloadTwitterTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_feed, container,
				false);

		ab.setTitle("UoN Freshers");

		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);

		progressBar.setVisibility(View.VISIBLE);

		swipeLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(mOnRefreshListener);
		swipeLayout.setColorScheme(R.color.category_mature_color,
				R.color.category_free_color, R.color.category_off_campus_color,
				R.color.category_night_out_color);

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		downloadTweets();

	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (downloadTwitterTask != null)
			downloadTwitterTask.cancel(true);
	}

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

	protected void adaptData() {

		progressBar.setVisibility(View.INVISIBLE);

		if (swipeLayout.isRefreshing()) {
			Log.i(null, "refresh called");
			swipeLayout.setRefreshing(false);
			((FeedAdapter) getListView().getAdapter()).refillTweets(statuses);

		}

		if (getListView().getAdapter() == null) {
			Log.i(null, "firstlistview called");
			adapter = new FeedAdapter(getActivity(), statuses);
			getListView().setOnScrollListener(new endlessScrollListener());
			getListView().setAdapter(adapter);
		} else {

			Log.i(null, "refill called");
			Log.i(null, "status amount sent: " + statuses.size());
			((FeedAdapter) getListView().getAdapter()).refillTweets(statuses);
			

		}

	}

	protected class DownloadTwitterTask extends
			AsyncTask<String, Void, List<twitter4j.Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(String... arg0) {

			return getUserTimeline();

		}

		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {

			if (statuses != null){
				
				List<twitter4j.Status> oldTweets = result;
				
				oldTweets.addAll(statuses);
				
				statuses = oldTweets;
			}
			else {
				Log.i(null, "FIRST REQUEST");
				statuses = result;
			}
			adaptData();
		}

		private List<twitter4j.Status> getUserTimeline() {

			List<twitter4j.Status> tempStatuses = null;

			try {
				// gets Twitter instance with default credentials\

				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
						.setOAuthConsumerKey("B4VPpnbx7Uz7S61XNsS316qxC")
						.setOAuthConsumerSecret(
								"skIOIJMZLrkBfJKIDL4CR6PjyoTYAHMEBb7KolzbszQ7FtBHcR")
						.setApplicationOnlyAuthEnabled(true); // IMPORTANT: set
																// T4J to use
																// App-only auth

				TwitterFactory tf = new TwitterFactory(cb.build());
				Twitter twitter = tf.getInstance();

				OAuth2Token token = twitter.getOAuth2Token();

				String user = "UoNFreshers";
				
				pager.setCount(pagecount);
				tempStatuses = twitter.getUserTimeline(user,pager);

			} catch (TwitterException te) {
				te.printStackTrace();

				Log.e(null, "failed to get timeline");
			}

			return tempStatuses;

		}
	}

	public class endlessScrollListener extends EndlessScrollListener {

		@Override
		public void onLoadMore(int page, int totalItemsCount) {

			customLoadMoreDataFromApi(page);

		}

		// Append more data into the adapter
		public void customLoadMoreDataFromApi(int offset) {
			// This method probably sends out a network request and appends new
			// data items to your adapter.
			// Use the offset value and add it as a parameter to your API
			// request to retrieve paginated data.
			// Deserialize API response and then construct new objects to append
			// to the adapter

			Log.i(null, "offset = "+offset);
			pagecount = offset;
			downloadTweets();

		}
	}

	protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			downloadTweets();
		}
	};

}