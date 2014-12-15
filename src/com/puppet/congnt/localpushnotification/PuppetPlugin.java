package com.puppet.congnt.localpushnotification;

import com.puppet.congnt.localpushnotification.service.ServicePushNotification;
import com.puppet.congnt.localpushnotification.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PuppetPlugin extends PuppetPluginBase {
	public static LocalPush pushObj;

	private static final String TAG = PuppetPlugin.class.getSimpleName();

	public void sendPushNotification(String title, String message, int timeAsSecond) {
		Intent intent = new Intent(Constants.ACTION_RUN_LOCAL_PUSH_NOTIFICATION);
		Bundle bundle = new Bundle();
		bundle.putString(Constants.TITLE, title);
		bundle.putInt(Constants.TIME, timeAsSecond);
		bundle.putString(Constants.MESSAGE, message);
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	public void clearPushNotification(){
		Intent intent = new Intent(Constants.ACTION_DESTROY_PUSH_NOTIFICATION);
		intent.setClass(getActivity(), ServicePushNotification.class);
		getActivity().startService(intent);
		
	}
}
