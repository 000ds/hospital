package com.cyansoft.maodou_manger.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class AddClassActivity extends AppCompatActivity implements View.OnClickListener {
 private EditText t_cla,t_discrible;
    private Button btn_submit,btn_delete,btn_update;
    private ProgressDialog rDialog;
    private ImageView iback;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_class);
        initview();
        mHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String res=(String)msg.obj;

            }
        };
    }

    private void initview() {
     t_cla = (EditText) findViewById(R.id.cla_name);
        t_discrible = (EditText) findViewById(R.id.cla_discrible);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        
        iback = (ImageView) findViewById(R.id.img_back);
        iback.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                if (t_cla.getText().toString() == null || "".equals(t_cla.getText().toString().trim())) {
                    ShowToask("公告名字不能为空");
                    break;
                }else {
                    // 提示框
                    rDialog = new ProgressDialog(this);
                    rDialog.setTitle("提示");
                    rDialog.setMessage("正在提交,请稍后....");
                    rDialog.setCancelable(true);
                    rDialog.show();
                    JSONObject job=new JSONObject();
                    try {
                        job.put("flag","add");
                        job.put("name",t_cla.getText().toString());
                        job.put("comment",t_discrible.getText().toString());
                        getDataFromPost(job.toString());
                    } catch (JSONException e) {
                    }

                    rDialog.dismiss();

                    break;
                }
            case R.id.btn_delete:
                if (t_cla.getText().toString() == null || "".equals(t_cla.getText().toString().trim())) {
                    ShowToask("公告名字不能为空");
                    break;
                }else {
                    // 提示框
                    rDialog = new ProgressDialog(this);
                    rDialog.setTitle("提示");
                    rDialog.setMessage("正在提交,请稍后....");
                    rDialog.setCancelable(true);
                    rDialog.show();
                    JSONObject job=new JSONObject();
                    try {
                        job.put("flag","delete");
                        job.put("name",t_cla.getText().toString());
                        getDataFromPost(job.toString());
                    } catch (JSONException e) {
                    }

                    rDialog.dismiss();

                    break;
                }


            case R.id.btn_update:
                if (t_cla.getText().toString() == null || "".equals(t_cla.getText().toString().trim())) {
                    ShowToask("公告名字不能为空");
                    break;
                }else {
                    // 提示框
                    rDialog = new ProgressDialog(this);
                    rDialog.setTitle("提示");
                    rDialog.setMessage("正在提交,请稍后....");
                    rDialog.setCancelable(true);
                    rDialog.show();
                    JSONObject job=new JSONObject();
                    try {
                        job.put("flag","update");
                        job.put("name",t_cla.getText().toString());
                        job.put("comment",t_discrible.getText().toString());
                        getDataFromPost(job.toString());
                    } catch (JSONException e) {
                    }

                    rDialog.dismiss();
                    break;
                }

        }
    }
    private void getDataFromPost(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.BAR_URL, json);
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
        Toast.makeText(AddClassActivity.this, name, Toast.LENGTH_SHORT).show();
    }
}
