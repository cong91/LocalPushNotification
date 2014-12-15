package com.puppet.congnt.localpushnotification;

import com.puppet.congnt.localpushnotification.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SendPushNotification extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		setContentView(layout);
		Button btn = new Button(this);
		LayoutParams paramsButton = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		btn.setLayoutParams(paramsButton);;
		btn.setText("Hit Me Push");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Constants.ACTION_RUN_LOCAL_PUSH_NOTIFICATION);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.TITLE, "fuck me");
				bundle.putInt(Constants.TIME, 2);
				bundle.putString(Constants.MESSAGE, "yeah");
				intent.putExtras(bundle);
				startService(intent);
				
			}
		});
		layout.addView(btn);
	}

}
