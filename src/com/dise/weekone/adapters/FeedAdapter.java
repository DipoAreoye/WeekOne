package com.dise.weekone.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dise.weekone.R;
import com.dise.weekone.Twitter.Tweet;

public class FeedAdapter extends ArrayAdapter<Tweet> {

	protected Context mContext;

	protected List<Tweet> twitter;
	protected ViewHolder holder;
	protected Resources r;
	public static boolean REACHED_THE_END;
	protected String regexAgo = "\\s*\\bago\\b\\s*",
			regexhr = "\\s*\\bhours\\b\\s*", regexDay = "\\s*\\bdays\\b\\s*";

	public FeedAdapter(Context context, List<Tweet> twitter) {
		super(context, R.layout.feed_item, twitter);

		mContext = context;
		this.twitter = twitter;
		r = mContext.getResources();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.feed_item, parent, false);

			holder = new ViewHolder();

			holder.tweet = (TextView) convertView.findViewById(R.id.tweetLabel);
			holder.time = (TextView) convertView.findViewById(R.id.timeLabel);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Tweet tweet = twitter.get(position);

		String tweetText = tweet.getText();
		holder.tweet.setText(tweetText);

		holder.time.setText(convertRelativeTime(tweet.getDateCreated()));

		if (position == getCount() - 1) {
			REACHED_THE_END = true;
		} else {
			REACHED_THE_END = false;
		}

		return convertView;
	}

	protected String convertRelativeTime(String time) {

		final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

		SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
		sf.setLenient(true);
		try {

			Date tweetTime = sf.parse(time);
			long now = new Date().getTime();
			String convertedDate = DateUtils.getRelativeTimeSpanString(
					tweetTime.getTime(), now, DateUtils.HOUR_IN_MILLIS)
					.toString();

			convertedDate = convertedDate.replaceAll(regexAgo, "")
					.replaceAll(regexDay, "d").replaceAll(regexhr, "h")
					.replaceAll(" ", "");

			return convertedDate;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void refillTweets(List<Tweet> twitter) {

		this.twitter.clear();
		this.twitter.addAll(twitter);
		notifyDataSetChanged();
		Log.i(null, twitter.size() + " counter");

	}

	private static class ViewHolder {

		TextView tweet;
		TextView time;

	}

}
