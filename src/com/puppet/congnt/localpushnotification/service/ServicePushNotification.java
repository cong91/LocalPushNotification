package com.puppet.congnt.localpushnotification.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.puppet.congnt.localpushnotification.R;
import com.puppet.congnt.localpushnotification.utils.Constants;

public class ServicePushNotification extends IntentService {

	public ServicePushNotification() {
		super("com.puppet.congnt.localpushnotification.service");
		// TODO Auto-generated constructor stub
	}

	public static final int NOTIFICATION_ID = 1602;
	private static final int SECOND = 1000;
	private static final String TAG = "Puppet ";
	Handler handler;
	private NotificationManager mNotificationManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}

	private void executeCommand(Intent intent) {
		String action = intent.getAction();
		if (action.equals(Constants.ACTION_RUN_LOCAL_PUSH_NOTIFICATION)) {
			Log.i(TAG, "======>  Receiver Action "
					+ Constants.ACTION_RUN_LOCAL_PUSH_NOTIFICATION);
			int time = intent.getExtras().getInt(Constants.TIME);
			final String message = intent.getExtras().getString(
					Constants.MESSAGE);
			final String title = intent.getExtras().getString(Constants.TITLE);
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					sendNotification(message, title);
				}
			};
			runThreadOnTime(runnable, time * SECOND);
		} else if (action.equals(Constants.ACTION_DESTROY_PUSH_NOTIFICATION)) {
			destroyNotification();
		}
	}

	private void runThreadOnTime(Runnable runable, long time) {
		handler.postDelayed(runable, time);
	}

	private void destroyNotification() {
		handler.removeCallbacksAndMessages(null);
	}

	private void sendNotification(String msg, String title) {
		Log.i(TAG, "======> yeah yeah  " + msg + " ////// " + title);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		final PackageManager pm = getPackageManager();
		ApplicationInfo applicationInfo = null;
		int appIconResId = -1;
		try {
			applicationInfo = pm.getApplicationInfo(getPackageName(),
					PackageManager.GET_META_DATA);
			// final Resources
			// resources=pm.getResourcesForApplication(applicationInfo);
			appIconResId = applicationInfo.icon;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent launchIntent = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		String className = launchIntent.getComponent().getClassName();
		Intent intent = new Intent();
		intent.setClassName(getApplicationContext(), className);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(appIconResId).setContentTitle(title)
				.setContentText(msg).setDefaults(Notification.DEFAULT_ALL)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		stopSelf();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		executeCommand(intent);

	}

}
