package com.cyansoft.maodou_manger.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

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

public class SingleActivity extends Activity implements OnClickListener {
    private EditText mEditText_name, mEditText_pwd, mEditText_surepwd;
    private Button mSubmitButton;
    private ProgressDialog rDialog;
    private ImageView selectImg;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_activity);
        initview();

        mHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String res=(String)msg.obj;
                if("注册成功！".equals(res)){
                    //记住用户名、密码、
                    Intent in = new Intent(SingleActivity.this, LoginActivity.class);
                    startActivity(in);
                    rDialog.dismiss();
                    Toast.makeText(SingleActivity.this, res, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SingleActivity.this, res, Toast.LENGTH_SHORT).show();
                }


            }
        };
    }

    private void initview() {
        mEditText_name = (EditText) findViewById(R.id.NickName_Single);
        mEditText_pwd = (EditText) findViewById(R.id.PassWord_Single);

        mEditText_surepwd = (EditText) findViewById(R.id.PassWord_AginSingle);

        mSubmitButton = (Button) findViewById(R.id.conmmit_Button);
        selectImg = (ImageView) findViewById(R.id.userImage_single_zhuce);
        selectImg.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userImage_single_zhuce:

                break;
            case R.id.conmmit_Button:
                if (mEditText_name.getText().toString() == null || "".equals(mEditText_name.getText().toString().trim())) {
                    ShowToask("用户名不能为空");
                    break;
                }
                if (mEditText_pwd.getText().toString() == null || "".equals(mEditText_pwd.getText().toString().trim())) {
                    ShowToask("密码不能为空");
                    Log.e("tag", "======Password");
                    break;
                }

                if (mEditText_surepwd.getText().toString() == null || "".equals(mEditText_surepwd.getText().toString().trim())) {
                    ShowToask("确认密码不能为空");
                    break;
                }
                if (!mEditText_surepwd.getText().toString().equals(mEditText_pwd.getText().toString())) {
                    ShowToask("确认密码和密码不一致,请重新输入");
                    mEditText_surepwd.setText("");
                    break;
                }

                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在注册,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();
                JSONObject job=new JSONObject();
                try {
                    job.put("flag","add");
                    job.put("name",mEditText_name.getText().toString());
                    job.put("password",mEditText_pwd.getText().toString());
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }
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
        Toast.makeText(SingleActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}
