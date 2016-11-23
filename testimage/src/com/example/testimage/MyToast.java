package com.example.testimage;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {
//	public MyToast(Context context, String str) {
//		Toast toast = new Toast(context);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		View view = View.inflate(context, R.layout.toast_textview_layout, null);
//		TextView tvToast = (TextView) view.findViewById(R.id.tv_toast);
//		toast.setText(str);
//		toast.setView(view);
//		toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
//		toast.show();
//	}
	
	public static Toast myText(Context context, String str, int duration) {
		Toast result = new Toast(context);
		View view = View.inflate(context, R.layout.toast_textview_layout, null);
		TextView textView = (TextView) view.findViewById(R.id.tv_toast);
		textView.setText(str);
		result.setView(view);
		result.setDuration(duration);
		result.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 130);
		return result;
	}
	
	public static Toast myTextSubmit(Context context, String str, int duration) {
		Toast result = new Toast(context);
		View view = View.inflate(context, R.layout.toast_textview_layout, null);
		view.setBackgroundResource(R.drawable.toastsubmit);
		TextView textView = (TextView) view.findViewById(R.id.tv_toast);
		textView.setText(str);
		result.setView(view);
		result.setDuration(duration);
		result.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 130);
		return result;
	}
}
