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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Brief extends Activity{

	private TextView briefText;
	private EditText etname,etage;
	private Button startButton;
	private RadioGroup radioGroup;
	private RadioButton maleButton,femaleButton;
	String tableName,tableName0,brief,sex;
	int count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brief);
		
		Intent intent = getIntent();
		tableName0 = intent.getStringExtra("tableName");
		System.out.println(tableName0);
		if(tableName0.equals("克氏行为量表")){
			tableName = "ksxwlb";
		}
		else if(tableName0.equals("儿童社交焦虑量表")){
			tableName = "etsjjllb";
		}
		else if(tableName0.equals("青少年忧郁情绪自我检视表")){
			tableName = "qsnyyqxzwjsb";
		}
		else if(tableName0.equals("儿童多动症行为量表")){
			tableName = "etddzxwlb";
		}
		System.out.println(tableName);
		
		JSONObject jsonObj;
		try
		{
			jsonObj = query(tableName,null,null,null);
			brief = jsonObj.getString("LB_content");
			count = jsonObj.getInt("count");
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		
		briefText = (TextView)findViewById(R.id.briefText);
		etname = (EditText)findViewById(R.id.nameText);
		etage = (EditText)findViewById(R.id.ageText);
		startButton = (Button)findViewById(R.id.startButton);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		maleButton = (RadioButton)findViewById(R.id.male);
		femaleButton = (RadioButton)findViewById(R.id.female);
		
		startButton.setOnClickListener(new startButtonListener());
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		
		briefText.setText(brief);
	}
	
	class radioGroupListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if(checkedId == maleButton.getId()){
				sex = "0";
				System.out.println(sex);
			}
			else if(checkedId == femaleButton.getId()){
				sex = "1";
			}
		}
		
	}
	
	class startButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name = etname.getText().toString();
			String age = etage.getText().toString();
			if(count == 2){
				try {
					query(tableName,name,age,sex);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(Brief.this,twooptions.class);
				intent.putExtra("tableName",tableName);
				startActivity(intent);
				finish();
			}
			else if(count == 3){
				try {
					query(tableName,name,age,sex);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(Brief.this,threeoptions.class);
				intent.putExtra("tableName",tableName);
				startActivity(intent);
				finish();
			}
			else if(count == 4){
				try {
					query(tableName,name,age,sex);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(Brief.this,fouroptions.class);
				intent.putExtra("tableName",tableName);
				startActivity(intent);
				finish();
			}
		}
		
	}
	
	private JSONObject query(String tableName,String name,String age,String sex)
			throws Exception
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("tableName", tableName);
			map.put("childname", name);
			map.put("childage", age);
			map.put("childsex", sex);
			String url = "http://172.27.35.1/My_Child/admin.php/Question/question";
			return new JSONObject(HttpUtil.postRequest(url, map));
		}

}
