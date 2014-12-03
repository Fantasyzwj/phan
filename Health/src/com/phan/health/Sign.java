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

public class Sign extends Activity
{
	private EditText etName, etPass;
	private Button okButton, cancelButton;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);

		etName = (EditText) findViewById(R.id.usernameText);
		etPass = (EditText) findViewById(R.id.passwordText);
		okButton = (Button) findViewById(R.id.okButton);
		cancelButton = (Button) findViewById(R.id.cancelButton);

		okButton.setOnClickListener(new okListener());
		cancelButton.setOnClickListener(new cancelListener());
	}

	class okListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (validate())  
			{
				if (signPro() == 1)  
				{
					DialogUtil.showDialog(Sign.this, "ע��ɹ���", false);
					finish();
				}
				else
				{
					DialogUtil.showDialog(Sign.this, "�û����Ѵ��ڣ����������룡", false);
				}
			}
		}
		
	}
	
	class cancelListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				finish();
		}
		
	}
	
	private int signPro()
	{
		String username = etName.getText().toString();
		String userpwd = etPass.getText().toString();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username, userpwd);
			String name = jsonObj.getString("name");
			System.out.println("1:"+name);
			return jsonObj.getInt("flag");
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}
		return 0;
	}

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

	private JSONObject query(String username, String userpwd)
		throws Exception
	{
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("userpwd", userpwd);
		// ���巢�������URL
		String url = "http://172.27.35.1/My_Child/admin.php/Register/doReg";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}