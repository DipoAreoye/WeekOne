package com.dise.weekone.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buzzbox.mob.android.scheduler.NotificationMessage;
import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.dise.weekone.R;
import com.dise.weekone.feed.WebViewActivity;
import com.dise.weekone.util.Const;
import com.dise.weekone.util.ReminderTask;

public class EventViewFragment extends Fragment {

	protected ActionBar ab;
	protected TextView eInfo;
	protected TextView eTitle;
	protected TextView eTime;
	protected TextView eDesc;
	protected TextView eLocation;
	protected TextView btnRemind;
	protected String minute, hour, dayOfMonth, dayOfWeek;
	protected Bundle b;
	protected String startTime;
	protected String iD, eventUrl, title, day;
	protected String[] categories;
	protected MainActivity mainActivity;
	protected LinearLayout mainLayout, tagsContainer;
	protected Typeface btnFont;
	protected SharedPreferences spfReminder;
	protected boolean stageChanged;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final String REMINDER_SET = "- Reminder Set";
		View rootView = inflater.inflate(R.layout.fragment_events_view,
				container, false);

		mainActivity = (MainActivity) getActivity();

		btnFont = Typeface.createFromAsset(mainActivity.getAssets(),
				"Mockup-Regular.otf");
		ab = mainActivity.getActionBar();
		b = getArguments();

		dayOfMonth = b.getString(Const.EVENT_DAY_OF_WEEK);

		tagsContainer = (LinearLayout) rootView
				.findViewById(R.id.tagsContainer);

		mainLayout = (LinearLayout) rootView
				.findViewById(R.id.fragment_container);

		iD = b.getString(Const.EVENT_ID);

		btnRemind = (TextView) rootView.findViewById(R.id.btnRemind);

		if (mainActivity.isReminderSet(iD)) {

			Log.i(null, "yoooo");

			btnRemind.setText("- Reminder Set");
			btnRemind.setTextColor(Color.WHITE);
			btnRemind.setBackgroundResource(R.drawable.btn_reminder_set);

		}

		btnRemind.setTypeface(btnFont);
		btnRemind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!btnRemind.getText().equals(REMINDER_SET)) {
					// mainActivity.setReminder(iD);

					toggleReminderButtonSet();

				} else {
					// mainActivity.removeReminder(iD);
					toggleReminderButton();
				}

			}
		});

		title = b.getString(Const.EVENT_TITLE);
		ab.setTitle(title);

		eTime = (TextView) rootView.findViewById(R.id.eventTimeLabel);
		eTime.setTypeface(btnFont);
		day = b.getString(Const.EVENT_DATE);
		eTime.setText(day + " September " + b.getString(Const.EVENT_TIME));

		eDesc = (TextView) rootView.findViewById(R.id.eventDescLabel);
		eDesc.setText(b.getString(Const.EVENT_DESC));
		eDesc.setTypeface(btnFont);

		eLocation = (TextView) rootView.findViewById(R.id.eventLocationLabel);
		eLocation.setText(b.getString(Const.EVENT_LOCATION));
		eLocation.setTypeface(btnFont);

		eventUrl = b.getString(Const.EVENT_LINK);

		addCategoryTags();

		setHasOptionsMenu(true);

		parseDate();

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.findItem(R.id.web_view_item).setVisible(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case R.id.web_view_item:
			Log.i(null, "clickeeed");
			loadWebViwe();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void loadWebViwe() {

		if (eventUrl == null)
			return;
		Intent intent = new Intent(getActivity(), WebViewActivity.class);

		intent.setData(Uri.parse(eventUrl));
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);

	}

	protected void parseDate() {

		String date = (String) b.get(Const.EVENT_TIME);

		int endPos = date.indexOf("m");

		startTime = date.substring(0, endPos + 1);
		startTime = startTime.replaceAll("pm", " pm").replaceAll("am", " am");

		final String dateFormat = "EEEE dd-MM hh:mm aa";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		dayOfWeek = day.substring(0, day.indexOf(' '));

		String tempPattern = dayOfWeek + " " + dayOfMonth + "-" + "09" + " "
				+ startTime;

		Log.i(null, tempPattern);

		try {

			Date tempDate = sdf.parse(tempPattern);

			dayOfMonth = (String) android.text.format.DateFormat.format("dd",
					tempDate);

			minute = (String) android.text.format.DateFormat.format("mm",
					tempDate);

			hour = (String) android.text.format.DateFormat.format("kk",
					tempDate);

			dayOfWeek = ((String) android.text.format.DateFormat.format("EE",
					tempDate)).toUpperCase(Locale.ENGLISH);
			
			Log.i(null, dayOfWeek);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.i(null, "unable to parse date");
		}

	}

	protected void addCategoryTags() {

		categories = b.getStringArray(Const.EVENT_CATEGORIES);

		int counter = 0;

		for (String category : categories) {

			int categoryColour = Event.getCategoryColour(category);

			if (categoryColour == 0) {

				continue;
			}

			if (tagsContainer.getChildCount() == 2) {

				tagsContainer = new LinearLayout(mainActivity);
				tagsContainer.setOrientation(LinearLayout.HORIZONTAL);
				LayoutParams LLParams = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				tagsContainer.setGravity(Gravity.CENTER);
				tagsContainer.setLayoutParams(LLParams);
				mainLayout.addView(tagsContainer);

			}

			if (tagsContainer.getChildCount() < 2) {

				TextView tag = new TextView(mainActivity);
				tag.setText(category);
				GradientDrawable drawable = (GradientDrawable) (mainActivity
						.getResources().getDrawable(R.drawable.bg_long_tags_));

				drawable.setColor(mainActivity.getResources().getColor(
						Event.getCategoryColour(category)));

				tag.setBackground(drawable);
				LinearLayout.LayoutParams param;

				if (counter == categories.length - 1
						&& tagsContainer.getChildCount() == 0) {

					tag.setWidth(mainActivity.getPixles(150));
					param = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					tag.setLayoutParams(param);

				} else {
					tag.setWidth(0);
					param = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 0.5f);

				}
				tag.setLayoutParams(param);
				tag.setTextColor(Color.WHITE);
				tag.setTextSize(12);
				tag.setTypeface(btnFont);
				param.setMargins(26, 26, 26, 26);
				tag.setPadding(8, 8, 8, 8);
				tag.setGravity(Gravity.CENTER);

				tagsContainer.addView(tag);

			}

			counter++;
		}
	}

	protected void toggleReminderButton() {

		mainActivity.removeReminder(iD);

		btnRemind.setTextColor(mainActivity.getResources().getColor(
				R.color.weekOne_color));
		btnRemind.setText("+ Remind Me");
		btnRemind.setBackground(null);

	}

	protected void toggleReminderButtonSet() {

		mainActivity.setReminder(iD);
		mainActivity.isReminderSet(iD);
		setNotification();
		btnRemind.setText("- Reminder Set");
		btnRemind.setTextColor(Color.WHITE);
		btnRemind.setBackgroundResource(R.drawable.btn_reminder_set);

	}

	protected void setNotification() {
		
		SchedulerManager.getInstance().saveTask(mainActivity,
				10 + " " + 02 + " " + 15 + " 09 " + "MON",

				ReminderTask.class);
	
		SchedulerManager.getInstance()
				.restart(mainActivity, ReminderTask.class);

	}
	
	
	
}

}
