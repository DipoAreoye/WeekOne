package com.dise.weekone.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dise.weekone.R;

public class InfoAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private int id;
	private Typeface font;
	private String list[];

	public InfoAdapter(Context context, int textViewResourceId,
			String list[]) {
		super(context, textViewResourceId, list);
		mContext = context;
		id = textViewResourceId;
		this.list = list;
		font = Typeface.createFromAsset(context.getAssets(),
				"ufonts.com_gillsans.ttf");
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		View mView = v;
		if (mView == null) {

			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = vi.inflate(id, null);
		}
		
		String text = list[position];

		TextView textView = (TextView) mView.findViewById(R.id.list_menu_item);

		textView.setTypeface(font);
		textView.setText(text);

		return mView;
	}

}