package com.dise.weekone.ui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.dise.weekone.R;
import com.dise.weekone.R.string;
import com.dise.weekone.util.Const;

public class Event {

	protected String _id;
	protected String title;
	protected String link;
	protected String eventDesc;
	protected String time;
	protected String date;
	protected String location;
	protected List<String> categories;

	public Event() {
		categories = new ArrayList<String>();

	}

	public void setId(String _id) {
		this._id = _id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setlink(String link) {
		this.link = link;
	}

	public void setEventDescString(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void addCategory(String category) {

		if (getCategoryShort(category) != 0 || categories.isEmpty())
			categories.add(category);

	}

	public void removeCategory(int index) {

		categories.remove(index);
	}

	public String getId() {
		return _id;
	}

	public String getTitle() {
		return title;
	}

	public String getlink() {
		return link;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public String getTime() {
		return time;
	}

	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public List<String> getCategories() {
		return categories;
	}

	public final static int getCategoryColour(String category) {

		if (category.contains(Const.FREE)) {

			Log.i(null, "colour");
			return R.color.category_free_color;

		} else if (category.contains(Const.MEET_GREET)) {

			return R.color.category_meet_greet_color;

		} else if (category.contains(Const.MATURE)) {

			return R.color.category_mature_color;

		} else if (category.contains(Const.NON_ALCOHOLIC)) {

			return R.color.category_non_alcoholic_color;

		} else if (category.contains(Const.POST_GRAD)) {

			return R.color.category_post_grad_color;

		} else if (category.contains(Const.NIGHT_OUT)) {

			return R.color.category_night_out_color;

		} else if (category.contains(Const.MOVIE_NIGHT)) {

			return R.color.category_movie_night_color;

		} else if (category.contains(Const.FAITH)) {

			return R.color.category_faith_color;

		} else if (category.contains(Const.OFF_CAMPUS)) {

			return R.color.category_off_campus_color;

		} else if (category.contains(Const.ON_CAMPUS)) {

			return R.color.category_on_campus_color;

		} else if (category.contains(Const.SPORTS)) {

			return R.color.category_sports_color;

		} else if (category.contains(Const.TICKETED)) {

			return R.color.category_ticketed_color;

		} else if (category.contains(Const.DERBY)) {

			return R.color.category_derby_color;

		}

		return 0;
	}

	public static final int getCategoryShort(String category) {

		if (category.contains(Const.FREE)) {

			return R.string.category_free_short;

		} else if (category.contains(Const.MEET_GREET)) {

			return R.string.category_meet_greet_short;

		} else if (category.contains(Const.MATURE)) {

			return R.string.category_mature_short;

		} else if (category.contains(Const.NON_ALCOHOLIC)) {

			return R.string.category_non_alcoholic_short;

		} else if (category.contains(Const.POST_GRAD)) {

			return R.string.category_post_grad_short;

		} else if (category.contains(Const.NIGHT_OUT)) {

			return R.string.category_night_out_short;

		} else if (category.contains(Const.MOVIE_NIGHT)) {

			return R.string.category_movie_night_short;

		} else if (category.contains(Const.FAITH)) {

			return R.string.category_faith_short;

		} else if (category.contains(Const.OFF_CAMPUS)) {

			return R.string.category_off_campus_short;

		} else if (category.contains(Const.ON_CAMPUS)) {

			return R.string.category_on_campus_short;

		} else if (category.contains(Const.SPORTS)) {

			return R.string.category_sports_short;

		} else if (category.contains(Const.TICKETED)) {

			return R.string.category_ticketed_short;

		} else if (category.contains(Const.DERBY)) {

			return R.string.category_derby_short;

		}
		return 0;

	}

}
