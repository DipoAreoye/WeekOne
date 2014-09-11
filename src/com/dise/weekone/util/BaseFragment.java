package com.dise.weekone.util;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.dise.weekone.MainActivity;

public class BaseFragment extends ListFragment {

	protected MainActivity mainActivity;
	protected ActionBar ab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainActivity = (MainActivity) this.getActivity();
		ab = mainActivity.getActionBar();
	}

	public void setTitle(String title) {

		ab.setTitle(title);
	}

}