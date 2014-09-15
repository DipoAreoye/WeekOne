package com.dise.weekone.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dise.weekone.R;
import com.dise.weekone.adapters.InfoAdapter;
import com.dise.weekone.feed.WebViewActivity;
import com.dise.weekone.util.BaseFragment;
import com.dise.weekone.util.Const;

public class MapsFragment extends BaseFragment {

	protected ListView listView;
	String[] newsFeed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);

		ab.setTitle(R.string.maps_title);

		getList();

		return rootView;

	}

	protected void getList() {

		newsFeed = getResources().getStringArray(R.array.map_list);

		InfoAdapter adapter = new InfoAdapter(getActivity(),
				R.layout.info_item, newsFeed);

		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		switch (position) {
		case 0:
			loadMapView(Const.UNI_PARK_MAP, newsFeed[position]);
			break;
		case 1:
			loadMapView(Const.JUBILEE_MAP, newsFeed[position]);
			break;
		case 2:
			loadMapView(Const.STN_BON_MAP, newsFeed[position]);
		default:
			break;
		}

	}

	public void loadMapView(String map, String name) {

		Intent intent = new Intent(getActivity(), MapView.class);

		intent.putExtra(Const.MAP_NAME, map);
		intent.putExtra(Const.CAMPUS_NAME, name);

		startActivity(intent);

	}

}