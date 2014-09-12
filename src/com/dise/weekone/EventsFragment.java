package com.dise.weekone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dise.weekone.adapters.EventAdapter;
import com.dise.weekone.util.BaseFragment;
import com.dise.weekone.util.Const;

public class EventsFragment extends BaseFragment {

	public String eventsData[] = { "", "", "", "", "", "", "", "" };
	public List<List<Event>> eventsLists;
	public List<Event> selectedDate;
	protected ProgressBar progressBar;
	protected HorizontalScrollView scrollView;
	protected LinearLayout linearLayout;
	protected ViewGroup parent;
	protected Typeface font;
	protected SharedPreferences spfEventsData;
	protected GetEventsTask gEventsTask;

	protected Button prevSelectedButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_events, container,
				false);

		ab.setTitle("Week One Events");
		
		font = Typeface.createFromAsset(mainActivity.getAssets(),
				"primer print.ttf");
		
		linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);

		addEventButtons();

		spfEventsData = mainActivity.getSharedPreferences(
				Const.KEY_EVENTS_DATA, Context.MODE_PRIVATE);

		if (eventsLists == null) {

			progressBar.setVisibility(View.VISIBLE);
		}

		return rootView;

	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (gEventsTask != null)
			gEventsTask.cancel(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		eventsData = loadArray();

		if (eventsData.length > 1) {
			handleEventsResponse();
		}
		if (mainActivity.isNetworkAvailable()) {
			gEventsTask = new GetEventsTask();
			gEventsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					Const.EVENTS_URL);

		} else {
			progressBar.setVisibility(View.INVISIBLE);
		}

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(final View v) {

			if (selectedDate == null) {
				return;
			}

			for (int i = 0; i < linearLayout.getChildCount(); i++) {

				Button btn = (Button) linearLayout.getChildAt(i);

				btn.setBackgroundResource(android.R.color.white);

			}

			selectedDate = eventsLists.get(v.getId());

			Button selectedButton = (Button) linearLayout.findViewById(v
					.getId());
			selectedButton.setBackgroundResource(R.drawable.event_circle_shape);

			prevSelectedButton = selectedButton;

			adaptData();
		}

	};

	public void addEventButtons() {

		if (prevSelectedButton == null) {

			prevSelectedButton = (Button) linearLayout.getChildAt(0);

			linearLayout.getChildAt(0).setBackgroundResource(
					R.drawable.event_circle_shape);
		}

		int l = linearLayout.getChildCount();

		for (int i = 0; i < l; i++) {

			Button btn = (Button) linearLayout.getChildAt(i);
			btn.setTypeface(font);
			btn.setEnabled(true);
			btn.setOnClickListener(onClickListener);

			if (prevSelectedButton != null) {

				if (prevSelectedButton.getText().equals(btn.getText())) {
					btn.setBackgroundResource(R.drawable.event_circle_shape);
				}

			}
			btn.setId(i);

		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Event selectedEvent = selectedDate.get(position);

		String[] categories = new String[selectedEvent.getCategories().size()];
		categories = selectedEvent.getCategories().toArray(categories);

		Bundle args = new Bundle();
		args.putString(Const.EVENT_TITLE, selectedEvent.getTitle());
		args.putString(Const.EVENT_DESC, selectedEvent.getEventDesc());
		args.putString(Const.EVENT_DATE, selectedEvent.getDate());
		args.putString(Const.EVENT_TIME, selectedEvent.getTime());
		args.putString(Const.EVENT_LOCATION, selectedEvent.getLocation());
		args.putString(Const.EVENT_LINK, selectedEvent.getlink());
		args.putString(Const.EVENT_ID, selectedEvent.getId());
		args.putStringArray(Const.EVENT_CATEGORIES, categories);

		mainActivity.addFragments(Const.EVENTS, new EventViewFragment(), args,
				true, true);

	}

	public void handleEventsResponse() {

		eventsLists = new ArrayList<List<Event>>();

		for (String rawData : eventsData) {

			eventsLists.add(parse(rawData));
		}

		if (selectedDate == null) {

			selectedDate = eventsLists.get(0);
		}
		adaptData();

	}

	public void adaptData() {

		
		
		
		EventAdapter adapter = new EventAdapter(getListView().getContext(),
				selectedDate);
		setListAdapter(adapter);

		adapter.notifyDataSetChanged();

	}

	public List<Event> parse(String data) {

		List<Event> tempEventList = null;

		Event event = null;

		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(data));
			tempEventList = new ArrayList<Event>();

			boolean insideItem = false;

			// Returns the type of current event: START_TAG, END_TAG, etc..
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					if (xpp.getName().equalsIgnoreCase(Const.TAG_XML_ITEM)) {
						insideItem = true;
						event = new Event();

					} else if (xpp.getName().equalsIgnoreCase(
							Const.TAG_XML_TITLE)) {
						if (insideItem)
							event.setTitle(xpp.nextText());
					} else if (xpp.getName().equalsIgnoreCase(
							Const.TAG_XML_LINK)) {
						if (insideItem)
							event.setlink(xpp.nextText());
					} else if (xpp.getName().equalsIgnoreCase(
							Const.TAG_XML_DESCRIPTION)) {
						if (insideItem) {
							event.setEventDescString(xpp.nextText());
						}
					} else if (xpp.getName().equalsIgnoreCase(
							Const.TAG_XML_CATEGORY)) {
						if (insideItem)
							event.addCategory(xpp.nextText());
					}

				} else if (eventType == XmlPullParser.END_TAG
						&& xpp.getName().equalsIgnoreCase(Const.TAG_XML_ITEM)) {
					event = parseXml(event);
					tempEventList.add(event);
				}

				eventType = xpp.next(); // / move to next element
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tempEventList;
	}

	private Event parseXml(Event event) {

		String desc = android.text.Html.fromHtml(event.getEventDesc())
				.toString();

		String lines[] = desc.split("\\r?\\n");

		event.setTime(lines[0]);

		if (lines.length > 3) {

			String combinedDesc = "";

			for (int i = 1; i < (lines.length - 2); i++) {

				combinedDesc += lines[i];

			}

			event.setEventDescString(combinedDesc);
			event.setLocation(lines[lines.length - 1]);

		} else {
			event.setEventDescString(lines[2]);
			event.setLocation("");
		}

		List<String> categories = event.getCategories();

		event.setDate(categories.get(0));

		event.removeCategory(0);

		event.setId(event.getTime() + event.getTitle() + event.getDate());

		return event;
	}

	public class GetEventsTask extends AsyncTask<String, Void, String[]> {
		@Override
		protected String[] doInBackground(String... urls) {

			String eventsDataArr[] = { "", "", "", "", "", "", "", "" };

			try {

				int i = 0;

				for (String url : urls) {

					eventsDataArr[i] = getSiteData(url);
					i++;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return eventsDataArr;

		}

		protected String getSiteData(String urlString) throws IOException {

			String data = "";

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpGet httpPost = new HttpGet(urlString);

			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity ht = response.getEntity();

			BufferedHttpEntity buf = new BufferedHttpEntity(ht);

			InputStream is = buf.getContent();

			BufferedReader r = new BufferedReader(new InputStreamReader(is));

			StringBuilder total = new StringBuilder();
			while ((data = r.readLine()) != null) {

				total.append(data + "\n");
			}

			return total.toString();

		}

		@Override
		protected void onPostExecute(String[] result) {

			SharedPreferences.Editor editor = spfEventsData.edit();
			editor.putString(Const.KEY_EVENTS_DATA, spfEventsData.toString());
			editor.commit();
			eventsData = result;
			progressBar.setVisibility(View.INVISIBLE);
			handleEventsResponse();
			cacheData(eventsData);

		}

	}

	public boolean cacheData(String[] data) {

		SharedPreferences.Editor editor = spfEventsData.edit();
		editor.putInt(Const.KEY_EVENTS_DATA + "_size", eventsData.length);
		for (int i = 0; i < eventsData.length; i++)
			editor.putString(Const.KEY_EVENTS_DATA + "_" + i, eventsData[i]);

		return editor.commit();

	}

	public String[] loadArray() {

		int size = 0;
		size = spfEventsData.getInt(Const.KEY_EVENTS_DATA + "_size", 0);

		String array[] = new String[size];
		for (int i = 0; i < size; i++)
			array[i] = spfEventsData.getString(Const.KEY_EVENTS_DATA + "_" + i,
					null);

		return array;

	}

}