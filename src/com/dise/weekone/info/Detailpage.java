package com.dise.weekone.info;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dise.weekone.R;
import com.dise.weekone.util.Const;

public class Detailpage extends Fragment {

	protected ActionBar ab;
	protected TextView mInfo;
	protected Bundle b;
	protected Typeface font;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.placeholder_main, container,
				false);

		ab = getActivity().getActionBar();

		mInfo = (TextView) rootView.findViewById(R.id.detailTextView);

		b = getArguments();

		ab.setTitle(b.getString(Const.AB_TITLE));

		mInfo.setText(b.getString(Const.DETAIL_PARAGRAPH));

		font = Typeface.createFromAsset(getActivity().getAssets(),
				"ufonts.com_gillsans.ttf");

		mInfo.setTypeface(font);

		return rootView;

	}

}
