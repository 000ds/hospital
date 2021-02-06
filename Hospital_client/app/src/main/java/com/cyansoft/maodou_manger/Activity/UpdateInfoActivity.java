package com.cyansoft.maodou_manger.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText t_name, t_pwd;
    private TextView t_back;
    private Button submit;
    private MyApplication myApplication;
    private ProgressDialog rDialog;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_info);
        myApplication = (MyApplication) getApplication();
        if (!myApplication.isLogin){
            Toast.makeText(UpdateInfoActivity.this,"请登录后在查看个人信息!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateInfoActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        initview();
    }

    private void initview() {
        t_name = (EditText) findViewById(R.id.user_name);
        t_pwd = (EditText) findViewById(R.id.user_pwd);

        submit = (Button) findViewById(R.id.update_btn);
        t_back = (TextView) findViewById(R.id.back_text);
        t_name.setText(myApplication.getUsername());
        t_pwd.setText(myApplication.getUserPwd());
        submit.setOnClickListener(this);
        t_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_text:
                finish();
                break;
            case R.id.update_btn:
                if (t_name.getText().toString() == null || "".equals(t_name.getText().toString().trim())) {
                    ShowToask("用户名不能为空");
                    break;
                }
                if (t_pwd.getText().toString() == null || "".equals(t_pwd.getText().toString().trim())) {
                    ShowToask("密码不能为空");
                    Log.e("tag", "======Password");
                    break;
                }
                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在更改,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();

                String name = t_name.getText().toString();
                String pwd = t_pwd.getText().toString();

                JSONObject job=new JSONObject();
                try {
                    job.put("flag","update");
                    job.put("name",name);
                    job.put("password",pwd);
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }
                rDialog.dismiss();
                break;
        }
    }
    private void getDataFromPost(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.REGISTER_URL, json);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void ShowToask(String name) {
        Toast.makeText(UpdateInfoActivity.this, name, Toast.LENGTH_SHORT).show();
    }
}
