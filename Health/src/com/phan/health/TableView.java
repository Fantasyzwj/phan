package com.phan.health;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TableView extends Activity{

	private Button button0,button1,back;
	String table0,table1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tableview);
		
		Intent intent = getIntent();
		String tableType = intent.getStringExtra("tableType");
		
		JSONObject jsonObj;
		try
		{
			jsonObj = query(tableType);
			table0 = jsonObj.getString("11");
			table1 = jsonObj.getString("12");
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		
		button0 = (Button)findViewById(R.id.button0);
		button1 = (Button)findViewById(R.id.button1);
		back = (Button)findViewById(R.id.back);
		
		button0.setText(table0);
		button1.setText(table1);
		
		button0.setOnClickListener(new button0Listener());
		button1.setOnClickListener(new button1Listener());
		back.setOnClickListener(new backListener());
	}
	
	class button0Listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TableView.this,Brief.class);
			intent.putExtra("tableName",table0);
			startActivity(intent);
			finish();
		}
		
	}
	
	class button1Listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TableView.this,Brief.class);
			intent.putExtra("tableName",table1);
			startActivity(intent);
			finish();
		}
		
	}
	
	class backListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
	
	private JSONObject query(String tableType)
			throws Exception
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("tableType", tableType);
			String url = "http://172.27.35.1/My_Child/admin.php/Question/question";
			return new JSONObject(HttpUtil.postRequest(url, map));
		}

}
