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
			//0:������;1:����;2:�ɹ�;
			if (validate())  
			{
				if (loginPro() == 2)  
				{
					DialogUtil.showDialog(Login.this, "��¼�ɹ���", false);
					Intent intent = new Intent(Login.this,Main.class);
					startActivity(intent);
					finish();
				}
				else if(loginPro() == 0)
				{
					DialogUtil.showDialog(Login.this, "�û��������ڣ�", false);
				}
				else
				{
					DialogUtil.showDialog(Login.this, "�û�����������������������룡", false);
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
		// ��ȡ�û�������û���������
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
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}
		return 1;
	}

	// ���û�������û������������У��
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "�û����Ǳ����", false);
			return false;
		}
		String userpwd = etPass.getText().toString().trim();
		if (userpwd.equals(""))
		{
			DialogUtil.showDialog(this, "�����Ǳ����", false);
			return false;
		}
		return true;
	}

	// ���巢������ķ���
	private JSONObject query(String username, String userpwd)
		throws Exception
	{
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("userpwd", userpwd);
		// ���巢�������URL
		String url = "http://172.27.35.1/My_Child/admin.php/Login/doLogin";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}