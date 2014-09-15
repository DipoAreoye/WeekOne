package com.dise.weekone.util;

import android.content.ContextWrapper;

import com.buzzbox.mob.android.scheduler.NotificationMessage;
import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.dise.weekone.R;
import com.dise.weekone.ui.MainActivity;

public class ReminderTask implements Task {

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
				"Event title", "Event is coming up in 3 hours");
		notification.setNotificationIconResource(R.drawable.ic_launcher);
		notification.setNotificationClickIntentClass(MainActivity.class);

		res.addMessage(notification);

		return res;
	}
}
