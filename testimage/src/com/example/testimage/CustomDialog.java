package com.example.testimage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 自定义对话框
 * @author hui
 *
 */
public class CustomDialog extends Dialog {
	
	AlertDialog alertDialog;
	private TextView title;
	private Button positionButton;
	private Button negativeButton;
	private View mView;
	private TextView tv_alert;

	public CustomDialog(Context context) {
		super(context,R.style.dialog);
		setCustomDialog();
	}

	private void setCustomDialog() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.exit_dialog, null);
		title = (TextView) mView.findViewById(R.id.title);
//		tv_alert = (TextView) findViewById(R.id.tv_alert);
		positionButton = (Button) mView.findViewById(R.id.positionButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		super.setContentView(mView);
	}
	
	public void setAlert(String at) {
		tv_alert.setText(at);
	}
	
	public void setBackgroundLayout(int resID) {
		mView.setBackgroundResource(resID);
	}
	
	public void setPosittionButtonName(String posittionName) {
		positionButton.setText(posittionName);
	}
	
	public void setPtBtnBg(int resId) {
		positionButton.setBackgroundResource(resId);
	}
	
	public void setNvBtnBg(int resId) {
		negativeButton.setBackgroundResource(resId);
	}
	
	public void setNegativeButtonName(String negativeName) {
		negativeButton.setText(negativeName);
	}
	
	public void setTitle(String tt) {
		title.setText(tt);
	}
	/**
	 * 确定键监听器
	 */
	public void setOnPositiveListener(android.view.View.OnClickListener listener) {
		positionButton.setOnClickListener(listener);
	}
	/**
	 * 取消键监听器
	 */
	public void setOnNegativeListener(android.view.View.OnClickListener listener) {
		negativeButton.setOnClickListener(listener);
	}

}
