package com.dise.weekone.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.provider.CalendarContract.Reminders;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dise.weekone.R;
import com.dise.weekone.ui.Event;
import com.dise.weekone.ui.MainActivity;

public class EventAdapter extends ArrayAdapter<Event> {

	protected Context mContext;

	protected List<Event> mEvents;
	protected ViewHolder holder;
	protected Typeface tf;
	protected Resources r;
	protected Typeface fontTime;
	protected Typeface fontTitle;

	public EventAdapter(Context context, List<Event> events) {
		super(context, R.layout.event_item, events);

		fontTitle = Typeface.createFromAsset(context.getAssets(),
				"primer print.ttf");
		fontTime = Typeface.createFromAsset(context.getAssets(),
				"Mockup-Regular.otf");

		mContext = context;
		mEvents = events;
		r = mContext.getResources();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.event_item, null);

			holder = new ViewHolder();

			holder.eventTitle = (TextView) convertView
					.findViewById(R.id.eventTitle);
			holder.eventTitle.setTypeface(fontTitle);
			holder.eventTime = (TextView) convertView
					.findViewById(R.id.eventTime);
			holder.eventTime.setTypeface(fontTime);
			holder.tagsLayout = (LinearLayout) convertView
					.findViewById(R.id.tagsContainer);
			holder.reminderInicator = (ImageView) convertView
					.findViewById(R.id.reminderLabel);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Event event = mEvents.get(position);

		String title = event.getTitle();
		holder.eventTitle.setText(title);

		String time = event.getTime();
		String location = event.getLocation();
		location = location.replace("Location:", "");

		List<String> categories = event.getCategories();

		holder.tagsLayout.removeAllViews();

		for (String category : categories) {

			addCategoryTags(category);

		}

		holder.eventTime.setText(time + " : " + location);

		if (((MainActivity) mContext).isReminderSet(event.getId())) {

			holder.reminderInicator.setVisibility(View.VISIBLE);

		} else {

			holder.reminderInicator.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	protected void addCategoryTags(String category) {

		int shortTag = Event.getCategoryShort(category);

		if (shortTag == 0) {
			return;
		}

		TextView t = new TextView(mContext);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		int px;

		px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				6, r.getDisplayMetrics()));

		layoutParams.setMargins(20, 0, 20, 0);

		px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				25, r.getDisplayMetrics()));

		t.setWidth(px);
		t.setHeight(px);
		t.setTextSize(10);
		t.setTypeface(fontTime);
		t.setTextColor(Color.WHITE);
		t.setGravity(Gravity.CENTER);
		t.setLayoutParams(layoutParams);

		t.setText(mContext.getResources().getString(shortTag));

		GradientDrawable drawable = (GradientDrawable) (mContext.getResources()
				.getDrawable(R.drawable.circle_shape));

		drawable.setColor(mContext.getResources().getColor(
				Event.getCategoryColour(category)));

		t.setBackground(drawable);

		holder.tagsLayout.addView(t);

	}

	private static class ViewHolder {

		LinearLayout tagsLayout;
		TextView eventTitle;
		TextView eventTime;
		ImageView reminderInicator;

	}

}
