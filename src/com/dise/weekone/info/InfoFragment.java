package com.dise.weekone.info;

import android.content.Intent;
import android.net.Uri;
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
		Bundle args = new Bundle();
		
		switch (position) {
		case 0:
			
			mainActivity.addFragments(Const.INFO, new MapsFragment(), null,
					true, true);
			break;
		case 1:
			
			// loadJubileeMap
			break;
		case 2:
			
			mainActivity.addFragments(Const.INFO, new ContactsFragment(), null,
					true, true);
			break;
		case 3:
			
			Detailpage aboutUoNSU = new Detailpage();
			
			args.putString(Const.DETAIL_PARAGRAPH, mainActivity.getResources().getString(R.string.about_SU_desc));
			args.putString(Const.AB_TITLE,mainActivity.getResources().getString(R.string.about_title));

			mainActivity.addFragments(Const.INFO, aboutUoNSU, args, true, true);
			
			break;
		case 4:
			
			sendFeedback();
			break;
		case 5:

			Detailpage createdBy = new Detailpage();
			
			args.putString(Const.DETAIL_PARAGRAPH, mainActivity.getResources().getString(R.string.created_by_desc));
			args.putString(Const.AB_TITLE,mainActivity.getResources().getString(R.string.created_by_title));
			mainActivity.addFragments(Const.INFO, createdBy, args, true, true);
			break;
		default:
			break;
		}

	}

	public void sendFeedback() {

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", "dipoareoye@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WeekOneFeedback");
		startActivity(Intent.createChooser(emailIntent, "Send email..."));

	}
}