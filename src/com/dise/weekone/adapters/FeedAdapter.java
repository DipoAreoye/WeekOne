package com.dise.weekone.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
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

	public FeedAdapter(Context context, List<Tweet> twitter) {
		super(context, R.layout.feed_item,twitter);

		mContext = context;
		this.twitter = twitter;
		r = mContext.getResources();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.feed_item,parent, false);

			holder = new ViewHolder();

			holder.tweet = (TextView) convertView.findViewById(R.id.tweetLabel);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Tweet tweet = twitter.get(position);

		String tweetText = tweet.getText();
		
		Log.i(null,"yoooo:  "  + tweetText);
		
		holder.tweet.setText(tweetText);

		return convertView;
	}

	private static class ViewHolder {

		TextView tweet;

	}

}
