package com.dise.weekone.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.buzzbox.mob.android.scheduler.db.model.Notification;
import com.buzzbox.mob.android.scheduler.ui.TimeUtils;
import com.dise.weekone.R;
import com.dise.weekone.feed.FeedFragment;
import com.dise.weekone.util.Const;

public class MainActivity extends FragmentActivity implements
		OnTabChangeListener {

	protected TabHost mTabHost;
	protected String mSelectedTab;
	protected ActionBar bar;
	protected HashMap<String, ArrayList<Fragment>> hMapTabs;
	protected Button btnHeaderBack;
	public Typeface fontAb;
	public SharedPreferences spfReminder;
	public int screenWidth, screenHeight;
	static final String arrTabLabel[] = { Const.EVENTS, Const.FEED, Const.INFO };
	protected View arrTabs[] = new View[4];
	protected boolean menuitem = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		spfReminder = getSharedPreferences("spfReminder", Context.MODE_PRIVATE);

		bar = getActionBar();

		bar.setDisplayHomeAsUpEnabled(true);

		fontAb = Typeface
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

		// initializing tabs//

		hMapTabs = new HashMap<String, ArrayList<Fragment>>();

		for (String tabName : arrTabLabel) {

			hMapTabs.put(tabName, new ArrayList<Fragment>());

		}

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setup();
		initializeTabs();
		setListeners();

	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(id));

		return view;
	}

	/*
	 * create 3 tabs with name and image and add it to TabHost
	 */
	public void initializeTabs() {

		TabHost.TabSpec spec;

		spec = mTabHost.newTabSpec(Const.EVENTS);
		mTabHost.setCurrentTab(0);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.ic_tab_events));
		mTabHost.addTab(spec);

		mTabHost.setCurrentTab(1);
		spec = mTabHost.newTabSpec(Const.FEED);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.ic_feed));
		mTabHost.addTab(spec);

		mTabHost.setCurrentTab(2);
		spec = mTabHost.newTabSpec(Const.INFO);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.ic_tab_info));
		mTabHost.addTab(spec);

	}

	public void setListeners() {

		// Listener for Events tab//
		mTabHost.getTabWidget().getChildAt(0)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (hMapTabs.size() > 0) {

							if (mTabHost.getTabWidget().getChildAt(0)
									.isSelected()) {
								if (hMapTabs.get(Const.TABS[0]).size() > 1) {
									resetFragment();
								}
							}
							mTabHost.getTabWidget().setCurrentTab(0);
							mTabHost.setCurrentTab(0);
						}
					}
				});

		// Listener for Feed tab//

		mTabHost.getTabWidget().getChildAt(1)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (hMapTabs.size() > 0) {

							if (mTabHost.getTabWidget().getChildAt(1)
									.isSelected()) {
								if (hMapTabs.get(Const.TABS[1]).size() > 1) {
									resetFragment();
								}
							}
							mTabHost.getTabWidget().setCurrentTab(1);
							mTabHost.setCurrentTab(1);
						}
					}
				});

		/* Listener for Tab 3 */
		mTabHost.getTabWidget().getChildAt(2)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (hMapTabs.size() > 0) {

							if (mTabHost.getTabWidget().getChildAt(2)
									.isSelected()) {
								if (hMapTabs.get(Const.TABS[2]).size() > 1) {
									resetFragment();
								}
							}
							mTabHost.getTabWidget().setCurrentTab(2);
							mTabHost.setCurrentTab(2);
						}
					}
				});
	}

	public void addFragments(String tabName, Fragment fragment, Bundle args,
			boolean animate, boolean add) {
		if (add) {
			hMapTabs.get(tabName).add(fragment);
		}
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (animate) {
			ft.setCustomAnimations(android.R.anim.fade_in,
					android.R.anim.fade_out, 0, 0);
		}

		if (args != null) {

			fragment.setArguments(args);
		}

		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();

		shouldDisplayHomeUp();

	}

	public void removeFragment() {
		Fragment fragment = hMapTabs.get(mSelectedTab).get(
				hMapTabs.get(mSelectedTab).size() - 2);
		hMapTabs.get(mSelectedTab)
				.remove(hMapTabs.get(mSelectedTab).size() - 1);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
				0, 0);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();

		shouldDisplayHomeUp();

	}

	// reset fragment used when clicked on same tab
	private void resetFragment() {
		Fragment fragment = hMapTabs.get(mSelectedTab).get(0);
		hMapTabs.get(mSelectedTab).clear();
		hMapTabs.get(mSelectedTab).add(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();

		shouldDisplayHomeUp();

	}

	@Override
	public void onBackPressed() {

		if (hMapTabs.get(mSelectedTab).size() <= 1) {
			super.onBackPressed();
		} else {
			removeFragment();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.web_menu, menu);
		menu.findItem(R.id.web_view_item).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			onBackPressed();
			return true;

		case R.id.web_view_item:
			return false;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (hMapTabs.get(mSelectedTab).size() == 0) {
			return;
		}
		hMapTabs.get(mSelectedTab).get(hMapTabs.get(mSelectedTab).size() - 1)
				.onActivityResult(requestCode, resultCode, data);
	}

	public boolean isReminderSet(String iD) {

		Set<String> setOld = spfReminder.getStringSet(Const.SPF_REMIND, null);

		if (setOld.contains(iD)) {
						
			return true;
		}

		return false;
	}

	public void setReminder(String iD) {

		// Retrieve the values
		Set<String> set = spfReminder.getStringSet(Const.SPF_REMIND, null);
		List<String> iDs = new ArrayList<String>();

		if (set != null) {

			iDs.addAll(set);

		}

		iDs.add(iD);

		// Set the values
		Set<String> newSet = new HashSet<String>();
		newSet.addAll(iDs);
		SharedPreferences.Editor editor = spfReminder.edit();
		editor.putStringSet(Const.SPF_REMIND, newSet);
		editor.commit();

	}

	public void removeReminder(String iD) {

		// Retrieve the values
		Set<String> setOld = spfReminder.getStringSet(Const.SPF_REMIND, null);
		setOld.remove(iD);

		// Set the values
		Set<String> set = new HashSet<String>();
		set.addAll(setOld);
		SharedPreferences.Editor spfEditor = spfReminder.edit();
		spfEditor.putStringSet(Const.SPF_REMIND, set);
		spfEditor.commit();

	}

	@Override
	public void onTabChanged(String tabName) {
		// TODO Auto-generated method stub
		mSelectedTab = tabName;

		// ImageView imgStatus = (ImageView) findViewById(R.id.imgInfoIcon);
		//
		// // Get the color of the icon depending on system state
		// int iconColor = android.graphics.Color.BLACK
		// if (systemState == Status.ERROR)
		// iconColor = android.graphics.Color.RED
		// else if (systemState == Status.WARNING)
		// iconColor = android.graphics.Color.YELLOW
		// else if (systemState == Status.OK)
		// iconColor = android.graphics.Color.GREEN
		//
		// // Set the correct new color
		// imgView.setColorFilter(iconColor, Mode.MULTIPLY);

		if (hMapTabs.get(tabName).size() == 0) {

			if (tabName.equals(Const.EVENTS)) {
				addFragments(tabName, new EventsFragment(), null, false, true);
			} else if (tabName.equals(Const.FEED)) {
				addFragments(tabName, new FeedFragment(), null, false, true);
			} else if (tabName.equals(Const.INFO)) {
				addFragments(tabName, new PlaceholderFragment(), null, false,
						true);
			}
		} else {
			addFragments(
					tabName,
					hMapTabs.get(tabName).get(hMapTabs.get(tabName).size() - 1),
					null, false, false);
		}
	}

	public void shouldDisplayHomeUp() {

		boolean showHomeAsUp;

		if (hMapTabs.get(mSelectedTab).size() > 1)

			showHomeAsUp = true;
		else
			showHomeAsUp = false;

		bar.setDisplayHomeAsUpEnabled(showHomeAsUp);

	}

	/*
	 * If you want to start this activity from another
	 */
	public static void startUrself(Activity context) {
		Intent newActivity = new Intent(context, MainActivity.class);
		newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(newActivity);
		context.finish();
	}

	public boolean isNetworkAvailable() {

		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		boolean isAvailable = false;

		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}

		return isAvailable;
	}

//    protected void createCustomNotification(){
//    	
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
//        
//                
//                final Notification notification = new Notification(
//                                R.drawable.icon_notification_cards_clubs, 
//                                "Custom Notification Layout",
//                                System.currentTimeMillis());    
//                
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.buzzbox_notification);
//        contentView.setImageViewResource(R.id.icon, R.drawable.icon_notification_cards_clubs);
//        contentView.setTextViewText(R.id.title, "Subject: Hello");
//        contentView.setTextViewText(R.id.text, "Hello, this message is in a custom expanded view");
//        contentView.setTextViewText(R.id.time, TimeUtils.formatTime(System.currentTimeMillis(), this));
//        notification.contentView = contentView;
//
//        Intent notificationIntent = new Intent(this, HelloWorldActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        notification.contentIntent = contentIntent;
//        
//        
//        
//        mNotificationManager.notify(526536326, notification);
//    }
	
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.placeholder_main,
					container, false);
			return rootView;
		}
	}

	public int getPixles(int dp) {

		int pixles;

		pixles = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
						.getDisplayMetrics()));

		return pixles;

	}

}