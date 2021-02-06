package com.cyansoft.maodou_manger.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.Adapter.findAdapter;
import com.cyansoft.maodou_manger.JavaBean.BaeBean;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class DoctorDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView t_name, t_cla, t_phone, t_info, t_special, t_zhicheng, t_money;
    private ImageView iBack;
    private TextView t_into, t_buynow;
    private EditText editText;
    private MyApplication myApplication;
    private ImageView yuyue_Img;
    private Handler mHandler;
    String url_now;
    private List<BaeBean> mDataBeanList = new ArrayList<>();
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_doctor_details);
        myApplication = (MyApplication) getApplication();
//        myApplication.setUsername(name);
        initview();

        mHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String res=(String)msg.obj;
                getdata(res);
            }
        };
    }
    private void getdata(String res)
    {
        findAdapter adapter= new findAdapter(DoctorDetailsActivity.this,R.layout.finditem2,mDataBeanList);
        mListView.setAdapter(adapter);
        JSONObject jsonobject;
        try {
            JSONArray jsonarray = new JSONArray(res);
            for (int i = 0; i < jsonarray.length(); i++) {
                jsonobject = jsonarray.getJSONObject(i);
                BaeBean baeBean = new BaeBean(jsonobject.getString("name"),jsonobject.getString("comment"));
                adapter.add(baeBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initview() {
        mListView = (ListView) findViewById(R.id.comment_list);
        img = (ImageView) findViewById(R.id.doctor_img);
        t_name = (TextView) findViewById(R.id.doctor_name);
        t_cla = (TextView) findViewById(R.id.doctor_cla);
        t_info = (TextView) findViewById(R.id.doctor_info);
        t_phone = (TextView) findViewById(R.id.doctor_phone);
        editText = (EditText) findViewById(R.id.edit_comment);
        t_special = (TextView) findViewById(R.id.doctor_special);
        t_zhicheng = (TextView) findViewById(R.id.doctor_zhicheng);
        t_money = (TextView) findViewById(R.id.doctor_money);

        iBack = (ImageView) findViewById(R.id.img_back);
        t_into = (TextView) findViewById(R.id.doctor_addtocar);
        t_buynow = (TextView) findViewById(R.id.doctor_buy_now);



        iBack.setOnClickListener(this);
        t_into.setOnClickListener(this);
        t_buynow.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bds = intent.getExtras();
        if (intent != null) {
            String name = bds.getString("name");
            String cla = bds.getString("class");
            String phone = bds.getString("phone");
            String zhicheng = bds.getString("zhicheng");
            String resume = bds.getString("resume");
            String info = bds.getString("info");
            String special = bds.getString("special");
            String money = bds.getString("money");
            String state = bds.getString("state");
            String total = bds.getString("total");
            t_name.setText(name);
            t_cla.setText(cla);
            t_special.setText(special);
            t_zhicheng.setText(zhicheng);
            t_info.setText(resume);

            t_phone.setText(phone);
            t_money.setText(money);
            String imgUrl = bds.getString("photo");
            x.image().bind(img, imgUrl);
            url_now = imgUrl;

            JSONObject job=new JSONObject();
            try {
                job.put("flag","search");
                job.put("doctorname",name);

                getDataFromPost1(job.toString());
            } catch (JSONException e) {
            }
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.doctor_addtocar:
                if (myApplication.isLogin) {
//                    Toast.makeText(DoctorDetailsActivity.this, "预约成功!", Toast.LENGTH_SHORT).show();
                    String username = myApplication.getUsername().toString();
                    String doctorname = t_name.getText().toString();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate).toString();
                    String money = t_money.getText().toString();
                    String cla = t_cla.getText().toString();
                    if(!editText.getText().toString().equals(""))
                    {
                        JSONObject job=new JSONObject();
                        try {
                            job.put("flag","add");
                            job.put("name",username);
                            job.put("doctorname",doctorname);
                            job.put("time",time);
                            job.put("comment",editText.getText().toString());
                            getDataFromPost1(job.toString());
                        } catch (JSONException e) {
                        }
                    }
                    else
                    {

                        Toast.makeText(DoctorDetailsActivity.this, "内容为空", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    Toast.makeText(DoctorDetailsActivity.this, "请登录后再支付!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
                break;
            case R.id.doctor_buy_now:
                if (myApplication.isLogin) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("是否立即支付?");
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setMessage("支付金额为:" + t_money.getText().toString());
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String username = myApplication.getUsername().toString();
                            String doctorname = t_name.getText().toString();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String time = formatter.format(curDate).toString();
                            String money = t_money.getText().toString();
                            String cla = t_cla.getText().toString();
                            Toast.makeText(DoctorDetailsActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                            JSONObject job=new JSONObject();
                            try {
                                job.put("flag","add");
                                job.put("name",username);
                                job.put("doctorname",doctorname);
                                job.put("time",time);
                                job.put("money",money);
                                job.put("cla",cla);
                                job.put("url",url_now);
                                getDataFromPost(job.toString());
                            } catch (JSONException e) {
                            }
                            dialog.dismiss();

//                            android.os.Process.killProcess(android.os.Process.myPid());

                        }
                    }).setNegativeButton("取消", null);

                    builder.show();
                } else {
                    Toast.makeText(DoctorDetailsActivity.this, "请登录后再支付!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
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
                    String result = HttpUtils.okPost(Config.GETORDER_URL, json);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void getDataFromPost1(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.COMMENT_URL, json);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
