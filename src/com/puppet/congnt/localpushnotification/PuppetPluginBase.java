package com.puppet.congnt.localpushnotification;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public abstract class PuppetPluginBase {
	private static final String TAG = PuppetPluginBase.class.getSimpleName();
	private static PuppetPluginBase _instance;
	private Field _unityPlayerActivityField;
	private Class<?> _unityPlayerClass;
	private Method _unitySendMessageMethod;
    public static Activity _activity;
	public PuppetPluginBase() {
		try {
			this._unityPlayerClass = Class
					.forName("com.unity3d.player.UnityPlayer");
			this._unityPlayerActivityField = this._unityPlayerClass
					.getField("currentActivity");
			Class cls = this._unityPlayerClass;
			Class[] clsArr = new Class[3];
			clsArr[0] = String.class;
			clsArr[1] = String.class;
			clsArr[2] = String.class;
			this._unitySendMessageMethod = cls.getMethod("UnitySendMessage",
					clsArr);
		} catch (ClassNotFoundException e) {
			Log.i(TAG, new StringBuilder("could not find UnityPlayer class: ")
					.append(e.getMessage()).toString());
		} catch (NoSuchFieldException e2) {
			Log.i(TAG,
					new StringBuilder("could not find currentActivity field: ")
							.append(e2.getMessage()).toString());
		} catch (Exception e3) {
			Log.i(TAG, new StringBuilder(
					"unkown exception occurred locating getActivity(): ")
					.append(e3.getMessage()).toString());
		}
	}

	public static PuppetPluginBase getInstance() {
		if (_instance != null) {
			return _instance;
		}
		_instance = new PuppetPlugin();
		return _instance;
	}

	protected void UnitySendMessage(String go, String m, String p) {
		if (p == null) {
			p = "";
		}
		if (this._unitySendMessageMethod != null) {
			try {
				Method method = this._unitySendMessageMethod;
				Object[] objArr = new Object[3];
				objArr[0] = go;
				objArr[1] = m;
				objArr[2] = p;
				method.invoke(null, objArr);
			} catch (IllegalArgumentException e) {
				Log.i(TAG,
						new StringBuilder(
								"could not find UnitySendMessage method: ")
								.append(e.getMessage()).toString());
			} catch (IllegalAccessException e2) {
				Log.i(TAG,
						new StringBuilder(
								"could not find UnitySendMessage method: ")
								.append(e2.getMessage()).toString());
			} catch (InvocationTargetException e3) {
				Log.i(TAG,
						new StringBuilder(
								"could not find UnitySendMessage method: ")
								.append(e3.getMessage()).toString());
			}
		} else {
			Toast.makeText(
					getActivity(),
					new StringBuilder("UnitySendMessage:\n").append(m)
							.append("\n").append(p).toString(), 1).show();
			Log.i(TAG, new StringBuilder("UnitySendMessage: ").append(go)
					.append(", ").append(m).append(", ").append(p).toString());
		}
	}

	protected Activity getActivity() {
		if (this._unityPlayerActivityField == null) {
			return _activity;
		}
		try {
			Activity activity = (Activity) this._unityPlayerActivityField
					.get(this._unityPlayerClass);
			if (activity != null) {
				return activity;
			}
			Log.e(TAG,
					"Something has gone terribly wrong. The Unity Activity does not exist. This could be due to a low memory situation");
			return activity;
		} catch (Exception e) {
			Log.i(TAG, new StringBuilder("error getting currentActivity: ")
					.append(e.getMessage()).toString());
			return _activity;
		}
	}
}
