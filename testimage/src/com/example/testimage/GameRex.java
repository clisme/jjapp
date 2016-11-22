package com.example.testimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GameRex extends Activity {
	private FileInputStream fis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.game_rex_layout);
		WebView webView = (WebView) findViewById(R.id.wb);
		webView.clearView();
		webView.loadUrl(getString(R.string.help_url));
//		load(webView);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				// 根据传入的参数再去加载新的网页
//				view.loadUrl(url);
//				// 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
//				return true;
//			}
//		});
//		try {
//			InputStream help = getAssets().open("help.html");
////			new InputStreamReader(help);
//			webView.loadUrl("file://" + "/test/help.html");
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
//	 private void load(WebView mWebView) {  
//		  
//		  
//	        try {  
//	        	String basePath = "/test";
//	            StringBuilder content = new StringBuilder();  
//	            String path = basePath + "help.html"; 
//	            fis = new FileInputStream(path);  
//	            byte[] buffer = new byte[1024];  
//	            int len = 0;  
//	            while ((len = fis.read(buffer)) != -1) {  
//	                content.append(new String(buffer, 0, len, "gbk"));  
//	            }  
//	            // System.out.println(content);  
//	            mWebView.getSettings().setDefaultTextEncodingName("utf-8");  
//	            String baseUrl = "file://" + basePath; 
//	            mWebView.loadDataWithBaseURL(baseUrl, content.toString(), "text/html", "utf-8",  
//	                    null);  
//	        } catch (FileNotFoundException e) {  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	        } catch (IOException e) {  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	        }  
//	  
//	  
//	    } 
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
