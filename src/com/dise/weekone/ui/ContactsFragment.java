package com.dise.weekone.ui;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dise.weekone.R;

public class ContactsFragment extends Fragment {

	protected ActionBar ab;
	protected MainActivity mainActivity;
	protected LinearLayout linearLayout;
	protected Typeface font;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_contacts, container,
				false);

		mainActivity = (MainActivity) getActivity();

		ab = mainActivity.getActionBar();

		linearLayout = (LinearLayout) rootView.findViewById(R.id.linearlayout);

		font = Typeface.createFromAsset(mainActivity.getAssets(),
				"ufonts.com_gillsans.ttf");
		addContactInfo();

		return rootView;

	}

	public void addContactInfo() {

		String[] items = mainActivity.getResources().getStringArray(
				R.array.contact_info);

		int counter = 0;
		for (String item : items) {

			LinearLayout.LayoutParams param;
			param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);

			TextView contact = new TextView(mainActivity);

			contact.setText(item);
			contact.setLayoutParams(param);
			contact.setTextColor(mainActivity.getResources().getColor(R.color.weekOne_color));
			contact.setTextSize(16);
			contact.setTypeface(font);
			contact.setPadding(25, 25, 25, 25);

			if (item.contains("0115")) {

				contact.setTextColor(mainActivity.getResources().getColor(
						R.color.phone_number_color));
				contact.setOnClickListener(onClickListener);

			} else if (item.contains("@")) {
				contact.setTextColor(mainActivity.getResources().getColor(
						R.color.text_color));
			}

			linearLayout.addView(contact);
		}
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			TextView phoneNumber = (TextView) v;
			String number = (String) phoneNumber.getText();
			number = number.replace(" ", "");

			try {

				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:" + number));
				startActivity(callIntent);

			} catch (ActivityNotFoundException activityException) {
				Log.e("Calling a Phone Number", "Call failed",
						activityException);
			}

		}
	};

}