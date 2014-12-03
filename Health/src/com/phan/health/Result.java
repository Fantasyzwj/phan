package com.phan.health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity{
	private TextView resulttext;
	private Button homeButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
		String result = intent.getStringExtra("result");
		
		resulttext = (TextView)findViewById(R.id.resulttext);
		homeButton = (Button)findViewById(R.id.homeButton);
		resulttext.setText(result);
		
		ButtonListener Listener = new ButtonListener();
		homeButton.setOnClickListener(Listener);
	}
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(Result.this, Main.class);
			startActivity(intent);
			finish();
		}
		
	}
}
