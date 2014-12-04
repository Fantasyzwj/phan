package com.phan.health;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class fouroptions extends Activity{
	private TextView question;
	private RadioGroup radioGroup;
	private RadioButton firstButton;
	private RadioButton secondButton;
	private RadioButton thirdButton;
	private RadioButton forthButton;
	private Button nextButton;
	private Button backButton;
	private int questionnum;
	private String tablename;
	private int maxnum;
	private String[] questiontext = new String[100];
	private String[] ans = new String[100];	
	private static final String[] y=
		{"Question_0","Question_1","Question_2","Question_3","Question_4","Question_5","Question_6","Question_7","Question_8","Question_9","Question_10",
		"Question_11","Question_12","Question_13","Question_14","Question_15","Question_16","Question_17","Question_18","Question_19","Question_20",
		"Question_21","Question_22","Question_23","Question_24","Question_25","Question_26","Question_27","Question_28","Question_29","Question_30",
		"Question_31","Question_32","Question_33","Question_34","Question_35","Question_36","Question_37","Question_38","Question_39","Question_40",
		"Question_41","Question_42","Question_43","Question_44","Question_45","Question_46","Question_47","Question_48","Question_49","Question_50",
		"Question_51","Question_52","Question_53","Question_54","Question_55","Question_56","Question_57","Question_58","Question_59","Question_60",
		"Question_61","Question_62","Question_63","Question_64","Question_65","Question_66","Question_67","Question_68","Question_69","Question_70",
		"Question_71","Question_72","Question_73","Question_74","Question_75","Question_76","Question_77","Question_78","Question_79","Question_80",
		"Question_81","Question_82","Question_83","Question_84","Question_85","Question_86","Question_87","Question_88","Question_89","Question_90",
		"Question_91","Question_92","Question_93","Question_94","Question_95","Question_96","Question_97","Question_98","Question_99"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fouroptions);
		
		question = (TextView)findViewById(R.id.question);
		radioGroup = (RadioGroup)findViewById(R.id.options);
		firstButton = (RadioButton)findViewById(R.id.firstButton);
		secondButton = (RadioButton)findViewById(R.id.secondButton);
		thirdButton = (RadioButton)findViewById(R.id.thirdButton);
		forthButton = (RadioButton)findViewById(R.id.forthButton);
		nextButton = (Button)findViewById(R.id.nextButton);
		backButton = (Button)findViewById(R.id.backButton);
		
		Intent intent = getIntent();
		tablename = intent.getStringExtra("tableName");
		questionnum = 1;
		//评测前从服务器端读取数据
		JSONObject jsonObj;
		try
		{
			jsonObj = getdetial(tablename);
			String firstoption = jsonObj.getString("A1");
			String secondoption = jsonObj.getString("A2");
			String thirdoption = jsonObj.getString("A3");
			String forthoption = jsonObj.getString("A4");
			maxnum = jsonObj.getInt("questionnumber");
			for (int i=1;i<=maxnum;i++) {
				questiontext[i]=jsonObj.getString(y[i]);
				ans[i] = "0";
			}
			
			firstButton.setText(firstoption);
			secondButton.setText(secondoption);
			thirdButton.setText(thirdoption);
			forthButton.setText(forthoption);
			question.setText("1."+questiontext[1]);
			
		}
		catch (Exception e)
		{
			AlertDialog.Builder dialog=new AlertDialog.Builder(fouroptions.this);
			dialog.setMessage("服务器相应异常，请稍后再试").setPositiveButton("确定", null).show();
			e.printStackTrace();
		}
				
		
		RadioGroupListener Listener = new RadioGroupListener();
        radioGroup.setOnCheckedChangeListener(Listener);
		
        ButtonListener buttonListener = new ButtonListener();
        nextButton.setOnClickListener(buttonListener);
        backButton.setOnClickListener(buttonListener);
        
	}
	class RadioGroupListener implements android.widget.RadioGroup.OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == firstButton.getId()) ans[questionnum]="1";
			else if (checkedId == secondButton.getId()) ans[questionnum]="2";
			else if (checkedId == thirdButton.getId()) ans[questionnum]="3"; 
			else if (checkedId == forthButton.getId()) ans[questionnum]="4";
		}    	
    }
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int id = v.getId();
			radioGroup.clearCheck();
			if (id == backButton.getId()){			
				if (questionnum > 1) questionnum = questionnum - 1;
				if (questionnum == maxnum-1) nextButton.setText("下一题");
				getmemory();
			} else if (id == nextButton.getId()){
				if (questionnum <= maxnum-1){
					questionnum = questionnum + 1;
					if (questionnum == maxnum) nextButton.setText("提交");
					getmemory();
				} else {
					//提交问卷
					JSONObject jsonObj;
					try
					{
						jsonObj = getresult();
						String result = jsonObj.getString("result");
						Intent intent = new Intent(fouroptions.this,Result.class);
					    //向下一个activity传送结果
					    intent.putExtra("result", result);
					    startActivity(intent);
					    finish();
					}
					catch (Exception e)
					{
						AlertDialog.Builder dialog=new AlertDialog.Builder(fouroptions.this);
						dialog.setMessage("服务器相应异常，请稍后再试").setPositiveButton("确定", null).show();
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void getmemory(){
		question.setText(questionnum+"."+questiontext[questionnum]);
		if (ans[questionnum].equals("1")) firstButton.setChecked(true);
		else if (ans[questionnum].equals("2")) secondButton.setChecked(true);
		else if (ans[questionnum].equals("3")) thirdButton.setChecked(true);
		else if (ans[questionnum].equals("4")) forthButton.setChecked(true);
	}
	// 定义发送请求,获取量表信息的方法
	private JSONObject getdetial(String tablename)
			throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableneirong", tablename);
		// 定义发送请求的URL
		String url = "http://192.185.2.37/~wenbo/admin.php/Question/question";			// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	private JSONObject getresult()
			throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		for (int i=1;i<=maxnum;i++) map.put(y[i], ans[i]);
		map.put("tableneirong", tablename);
		// 定义发送请求的URL
		String url = "http://192.185.2.37/~wenbo/admin.php/Question/question";			// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}
