package com.dise.weekone.util;

public final class Const {
	
	public static final String KEY_EVENTS_DATA = "eventsData";

	public static final String EVENTS = "Events", FEED = "Feed", INFO = "Info";
	public static final String[] TABS = { EVENTS, FEED, INFO };
	
	
	//XML 
	public static final String TAG_XML_ITEM = "item";
	public static final String TAG_XML_TITLE = "title";
	public static final String TAG_XML_LINK = "link";
	public static final String TAG_XML_DESCRIPTION = "content:encoded";
	public static final String TAG_XML_CATEGORY = "category";

	//EVENTS
	public static final String[] EVENTS_URL = {
			"http://www.welcometonottingham2014.co.uk/category/sunday-21st/feed/",
			"http://www.welcometonottingham2014.co.uk/category/monday-22nd/feed/",
			"http://www.welcometonottingham2014.co.uk/category/tuesday-23rd/feed/",
			"http://www.welcometonottingham2014.co.uk/category/wednesday-24th/feed/",
			"http://www.welcometonottingham2014.co.uk/category/thursday-25th/feed/",
			"http://www.welcometonottingham2014.co.uk/category/friday-26th/feed/",
			"http://www.welcometonottingham2014.co.uk/category/saturday-27th/feed/",
			"http://www.welcometonottingham2014.co.uk/category/sunday-28th/feed/", };

	public static final int[] EVENT_DATES = { 21, 22, 23, 24, 25, 26, 27, 28 };

	public static final String EVENT_TITLE = "eTitle", EVENT_DESC = "eDesc",
			EVENT_DATE = "eDate", EVENT_LOCATION = "eLocation",
			EVENT_TIME = "eTime", EVENT_ID = "eId",
			EVENT_CATEGORIES = "eCategories", EVENT_LINK = "eLink",EVENT_DAY_OF_WEEK = "eDayOfWeek";
	//CATEGORIES
	public static final String FREE = "Free", MEET_GREET = "Meet and greet",
			MATURE = "Mature", NON_ALCOHOLIC = "Non-alcoholic",
			POST_GRAD = "Postgraduate", NIGHT_OUT = "Night out",
			MOVIE_NIGHT = "Movie night", FAITH = "Faith",
			OFF_CAMPUS = "Off campus", ON_CAMPUS = "On campus",
			SPORTS = "Sport", TICKETED = "Ticketed", DERBY = "Derby",
			HALLS = "Halls";
	
	//TWITTER
	
	public static final String UON_TWITTER_NAME = "UonFreshers";
	public static final String WEB_VIEW_URL = "webViewUrl";

	
	
	//DIMENS 
	
	//Sharedpref
	
	public static final String SPF_REMIND = "reminderiDs";



}
