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

	public CustomDialog(Context context) {
		super(context,R.style.dialog);
		setCustomDialog();
	}

	private void setCustomDialog() {
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.exit_dialog, null);
		title = (TextView) mView.findViewById(R.id.title);
		positionButton = (Button) mView.findViewById(R.id.positionButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		super.setContentView(mView);
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
