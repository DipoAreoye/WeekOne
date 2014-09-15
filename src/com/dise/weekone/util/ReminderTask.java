package com.dise.weekone.util;

import android.content.ContextWrapper;

import com.buzzbox.mob.android.scheduler.NotificationMessage;
import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.dise.weekone.R;
import com.dise.weekone.ui.MainActivity;

public class ReminderTask implements Task {
	
	protected String title;
	protected String message;

	public  ReminderTask(String title) {
		
		this.title = title;
		
	}
		
	
	@Override
	public String getTitle() {
		return "Event Comping up";
	}

	@Override
	public String getId() {
		return "identification";
	}

	@Override
	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();

		// TODO implement your business logic here
		// i.e. query the DB, connect to a web service using HttpUtils, etc..

		NotificationMessage notification = new NotificationMessage(
				title, message);
		notification.setNotificationIconResource(R.drawable.ic_welcome_logo);
		notification.setNotificationClickIntentClass(MainActivity.class);

		res.addMessage(notification);

		return res;
	}
}