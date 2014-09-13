package com.dise.weekone;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.dise.weekone.util.Const;
import com.dise.weekone.util.MainInterface;

public class MainActivity extends FragmentActivity implements
		OnTabChangeListener, MainInterface{

	protected TabHost mTabHost;
	protected String mSelectedTab;
	protected ActionBar bar;
	protected HashMap<String, ArrayList<Fragment>> hMapTabs;
	final int TEXT_ID = 100;
	protected Button btnHeaderBack;
	public Typeface fontAb;
	public int screenWidth,screenHeight;
	static final String arrTabLabel[] = { Const.EVENTS, Const.FEED, Const.INFO };
	protected View arrTabs[] = new View[4];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bar = getActionBar();
	
		fontAb = Typeface
				.createFromAsset(getAssets(), "Montserrat-Regular.ttf");

		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
				title.setTypeface(fontAb);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (hMapTabs.get(mSelectedTab).size() == 0) {
			return;
		}
		hMapTabs.get(mSelectedTab).get(hMapTabs.get(mSelectedTab).size() - 1)
				.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onTabChanged(String tabName) {
		// TODO Auto-generated method stub
		mSelectedTab = tabName;
		setScreenSizePixels();

		
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
				addFragments(tabName, new NewFeedFragment(), null, false, true);
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

	public void setScreenSizePixels() {

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		screenHeight = size.y;

	}

	@Override
	public int getScreenWidth(){
		return screenWidth;
	}
	
	public int getHeight(){
		return screenHeight;
	}
	
	public int getPixles(int dp) {

		int pixles;

		pixles = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
						.getDisplayMetrics()));

		return pixles;

	}

}