package com.dise.weekone.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dise.weekone.R;
import com.dise.weekone.adapters.InfoAdapter;
import com.dise.weekone.ui.MapsFragment;
import com.dise.weekone.util.BaseFragment;
import com.dise.weekone.util.Const;

public class InfoFragment extends BaseFragment {

	protected ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);

		ab.setTitle(R.string.Information_tab_title);

		getList();

		return rootView;

	}

	protected void getList() {

		String[] newsFeed = getResources().getStringArray(R.array.info_list);

		InfoAdapter adapter = new InfoAdapter(getActivity(),
				R.layout.info_item, newsFeed);

		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		switch (position) {
		case 0:
			mainActivity.addFragments(Const.INFO, new MapsFragment(), null,
					true, true);
			break;
		case 1:
			mainActivity.addFragments(Const.INFO, new )
			// loadJubileeMap

		default:
			break;
		}

	}
}