package com.dise.weekone;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dise.weekone.util.Const;

public class EventViewFragment extends Fragment {

	protected ActionBar ab;
	protected TextView eInfo;
	protected TextView eTitle;
	protected TextView eTime;
	protected TextView eDesc;
	protected TextView eLocation;
	protected Button btnRemind;
	protected Bundle b;
	protected String iD, eventUrl, title, fullDate;
	protected String[] categories;
	protected MainActivity mainActivity;
	protected LinearLayout mainLayout, tagsContainer;
	protected Button eType;
	protected Typeface btnFont;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_events_view,
				container, false);

		mainActivity = (MainActivity) getActivity();

		btnFont = Typeface.createFromAsset(mainActivity.getAssets(),
				"Mockup-Regular.otf");
		ab = mainActivity.getActionBar();
		b = getArguments();

		tagsContainer = (LinearLayout) rootView
				.findViewById(R.id.tagsContainer);

		mainLayout = (LinearLayout) rootView
				.findViewById(R.id.fragment_container);

		btnRemind = (Button) rootView.findViewById(R.id.btnRemind);
		btnRemind.setTypeface(btnFont);

		title = b.getString(Const.EVENT_TITLE);
		ab.setTitle(title);

		eTime = (TextView) rootView.findViewById(R.id.eventTimeLabel);
		eTime.setTypeface(btnFont);
		fullDate = b.getString(Const.EVENT_DATE);
		eTime.setText(fullDate + " September " + b.getString(Const.EVENT_TIME));

		eDesc = (TextView) rootView.findViewById(R.id.eventDescLabel);
		eDesc.setText(b.getString(Const.EVENT_DESC));
		eDesc.setTypeface(btnFont);

		eLocation = (TextView) rootView.findViewById(R.id.eventLocationLabel);
		eLocation.setText(b.getString(Const.EVENT_LOCATION));
		eLocation.setTypeface(btnFont);

		addCategoryTags();

		return rootView;

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
						.getResources()
						.getDrawable(R.drawable.button_reminder_set));

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
				tag.setTypeface(btnFont);
				param.setMargins(26, 26, 26, 26);
				tag.setPadding(8, 8, 8, 8);
				tag.setGravity(Gravity.CENTER);

				tagsContainer.addView(tag);

			}

			counter++;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	protected void toggleReminderButton() {

	}

	protected void toggleReminderButtonOff() {

	}

	protected void removeNotification() {

	}

	protected void setNotification() {
		//
		// Calendar cal = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm aa",
		// Locale.UK);
		//
		// Log.e(null, fullDate);
		//
		// try {
		// cal.setTime(sdf.parse(fullDate));
		// } catch (java.text.ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// // add 5 minutes to the calendar object
		// cal.add(Calendar.HOUR, -1);
		//
		// Intent intent = new Intent(getActivity(), AlarmReceiver.class);
		// intent.putExtras(b);
		//
		// // In reality, you would want to have a static variable for the
		// request
		// // code instead of 192837
		// PendingIntent sender = PendingIntent.getBroadcast(mainActivity,
		// 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//
		// // Get the AlarmManager service
		// AlarmManager am = (AlarmManager) mainActivity
		// .getSystemService(mainActivity.ALARM_SERVICE);
		// am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
	}
}
