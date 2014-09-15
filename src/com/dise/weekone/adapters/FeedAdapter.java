		package com.dise.weekone.adapters;
		
		import java.text.ParseException;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import java.util.List;
		import java.util.Locale;
		
		import twitter4j.Status;
		import android.content.Context;
		import android.graphics.Typeface;
		import android.text.format.DateUtils;
		import android.util.Log;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;
		import android.widget.TextView;
		
		import com.dise.weekone.R;
		import com.dise.weekone.ui.MainActivity;
		
		public class FeedAdapter extends GenericAdapter<Status> {
		
			protected Context mContext;
		
			protected List<Status> statusList;
			protected ViewHolder holder;
			protected Typeface defaultFont;
			protected String regexAgo = "\\s*\\bago\\b\\s*",
					regexhr = "\\s*\\bhours\\b\\s*", regexDay = "\\s*\\bdays\\b\\s*",
					regexmin = "\\s*\\bminutes\\b\\s*",
					regexYes = "\\s*\\bYesterday\\b\\s*";
		
			public FeedAdapter(Context context, List<Status> statuses) {
				super((MainActivity) context, statuses);
		
				defaultFont = Typeface.createFromAsset(context.getAssets(),
						"ufonts.com_gillsans.ttf");
				mContext = context;
				statusList = statuses;
			}
		
			@Override
			public View getDataRow(int position, View convertView, ViewGroup parent) {
				// ViewHolder holder;
		
				if (convertView == null) {
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.feed_item, parent, false);
		
					holder = new ViewHolder();
		
					holder.hashtag = (TextView) convertView
							.findViewById(R.id.hashtagLabel);
					holder.hashtag.setTypeface(defaultFont);
					holder.tweet = (TextView) convertView.findViewById(R.id.tweetLabel);
					holder.tweet.setTypeface(defaultFont);
					holder.time = (TextView) convertView.findViewById(R.id.timeLabel);
					holder.time.setTypeface(defaultFont);
					convertView.setTag(holder);
		
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
		
				Status tweet = statusList.get(position);
		
				String tweetText = tweet.getText();
				holder.tweet.setText(tweetText);
		
				holder.time.setText(convertRelativeTime(tweet.getCreatedAt()));
		
				return convertView;
			}
		
			protected String convertRelativeTime(Date time) {
		
				final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		
				SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
				sf.setLenient(true);
		
				long now = new Date().getTime();
				String convertedDate = DateUtils.getRelativeTimeSpanString(
						time.getTime(), now, DateUtils.MINUTE_IN_MILLIS).toString();
		
				return parseDate(convertedDate);
			}
		
			protected String parseDate(String oldDate) {
		
				String parsedDate = oldDate.replaceAll(regexAgo, "");
		
				if (parsedDate.contains("minutes")) {
					parsedDate = parsedDate.replaceAll(regexmin, "m");
		
				} else if (parsedDate.contains("hours")) {
					parsedDate = parsedDate.replaceAll(regexhr, "h");
		
				} else if (parsedDate.contains("Yesterday")) {
					parsedDate = parsedDate.replaceAll(regexYes, "1d");
		
				} else if (parsedDate.contains("days")) {
					parsedDate = parsedDate.replaceAll(regexDay, "d");
		
				} else if (parsedDate.length() > 4) {
		
					final String dateFormat = "MMMdd,yyyy";
					SimpleDateFormat sf = new SimpleDateFormat(dateFormat,
							Locale.ENGLISH);
					sf.setLenient(true);
		
					Date tempDate;
					try {
						tempDate = sf.parse(parsedDate);
						parsedDate = (String) android.text.format.DateFormat.format(
								"dd/MM/yy", tempDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
				}
		
				parsedDate.replaceAll(" ", "");
				return parsedDate;
		
			}
		
			private static class ViewHolder {
				TextView hashtag;
				TextView tweet;
				TextView time;
		
			}
		
			public void refillTweets(List<Status> twitterNew) {
		
				List<Status> newList = twitterNew;
				
				statusList.clear();
				statusList.addAll(newList);
				notifyDataSetChanged();
				
			}
		}
