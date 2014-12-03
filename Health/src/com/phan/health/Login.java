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

public class Login extends Activity
{
	private EditText etName, etPass;
	private Button okButton,signButton;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		etName = (EditText)findViewById(R.id.usernameText);
		etPass = (EditText)findViewById(R.id.passwordText);
		okButton = (Button)findViewById(R.id.okButton);
		signButton = (Button)findViewById(R.id.signButton);
		
		okButton.setOnClickListener(new okListener());
		signButton.setOnClickListener(new signButtonListener());
		
	}

	class okListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//0:不存在;1:错误;2:成功;
			if (validate())  
			{
				if (loginPro() == 2)  
				{
					DialogUtil.showDialog(Login.this, "登录成功！", false);
					Intent intent = new Intent(Login.this,Main.class);
					startActivity(intent);
					finish();
				}
				else if(loginPro() == 0)
				{
					DialogUtil.showDialog(Login.this, "用户名不存在！", false);
				}
				else
				{
					DialogUtil.showDialog(Login.this, "用户名或者密码错误，请重新输入！", false);
				}
			}
		}
	}
		
	class signButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Login.this, Sign.class);
			startActivity(intent);
		}
		
	}
	
	private int loginPro()
	{
		// 获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String userpwd = etPass.getText().toString();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username, userpwd);
			return jsonObj.getInt("flag");
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		return 1;
	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "用户名是必填项！", false);
			return false;
		}
		String userpwd = etPass.getText().toString().trim();
		if (userpwd.equals(""))
		{
			DialogUtil.showDialog(this, "密码是必填项！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private JSONObject query(String username, String userpwd)
		throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("userpwd", userpwd);
		// 定义发送请求的URL
		String url = "http://172.27.35.1/My_Child/admin.php/Login/doLogin";
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}