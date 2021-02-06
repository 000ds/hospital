package com.cyansoft.maodou_manger.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyansoft.maodou_manger.JavaBean.Doctor;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;
import com.cyansoft.maodou_manger.fragment.InfoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class SuosuoActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter = null;
    private static final String[] chose = {"科室搜索", "医生姓名"};
    private EditText sousuo_text;
    private Spinner spinner = null;
    private ListView cList_doctors;
    private String cla = null;
    private int id = 0;
    private List<Doctor> mList;
    private TextView sousuo_btn;
    private TextView back;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_suosuo);

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

    private void initview() {
        sousuo_text = (EditText) findViewById(R.id.sousuo_edittxt);
        cList_doctors = (ListView) findViewById(R.id.list_doctor);
        spinner = (Spinner) findViewById(R.id.spinner_chose);
        sousuo_btn = (TextView) findViewById(R.id.sousuo_btn);
        back = (TextView) findViewById(R.id.sousuo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chose);
        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到spinner中去
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);//设置默认显示
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                cla = ((TextView) arg1).getText().toString();
//                sousuo_text.setText("按" + ((TextView) arg1).getText());

                if (cla.equals("医生姓名")) {
                    id = 1;
                } else if (cla.equals("科室搜索")) {
                    id = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sousuo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sousuotext = sousuo_text.getText().toString();
                String path = null;
                JSONObject job=new JSONObject();
                try {

                    job.put("flag",cla);
                    job.put("content",sousuotext);
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }

                    cList_doctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SuosuoActivity.this, DoctorDetailsActivity.class);
                            Bundle bd = new Bundle();
                            bd.putString("name", mList.get(position).name);
                            bd.putString("class", mList.get(position).cla);
                            bd.putString("phone", mList.get(position).phone);
                            bd.putString("zhicheng", mList.get(position).zhichen);
                            bd.putString("resume", mList.get(position).resume);
                            bd.putString("info", mList.get(position).info);
                            bd.putString("special", mList.get(position).special);
                            bd.putString("photo", mList.get(position).getPhoto());
                            bd.putString("money", mList.get(position).money);
                            bd.putString("state",mList.get(position).state);
                            intent.putExtras(bd);
                            startActivity(intent);
                        }
                    });

            }
        });
    }
    private void getDataFromPost(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.SEARCH_URL, json);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void getdata(String res)
    {
        List<Doctor> doctorList = new ArrayList<>();
        JSONObject jsonobject;
        Doctor doctor;
        try {
//                jsonobject = new JSONObject(jsonString);
            JSONArray jsonarray = new JSONArray(res);
            for (int i = 0; i < jsonarray.length(); i++) {
                jsonobject = jsonarray.getJSONObject(i);
                doctor = new Doctor();
                doctor.setName(jsonobject.getString("name"));
//                    doctor.name = jsonobject.getString("username");
                doctor.cla = jsonobject.getString("classes");
                doctor.phone = jsonobject.optString("phone");
                doctor.zhichen = jsonobject.getString("zhicheng");
                doctor.resume = jsonobject.getString("resume");
                doctor.info = jsonobject.getString("info");
                doctor.special = jsonobject.getString("special");
                doctor.setPhoto(jsonobject.getString("photo"));
                doctor.money = jsonobject.getString("money");
                doctor.state = jsonobject.getString("state");
                doctor.total = jsonobject.getString("total");
                doctorList.add(doctor);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(), doctorList);
        cList_doctors.setAdapter(newsAdapter);
    }

    /**
     * NewsAdapter是listview的适配器
     */
    public class NewsAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        public NewsAdapter(Context context, List<Doctor> data) {
            mList = data;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.item_layout, null);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.username);
                viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.user_class);
                viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.dstate);
                viewHolder.mTextView4= (TextView) convertView.findViewById(R.id.total);
                viewHolder.mTextView5 = (TextView) convertView.findViewById(R.id.user_zhicheng);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String url = mList.get(position).photo;
            x.image().bind(viewHolder.ivIcon, url);
            viewHolder.mTextView1.setText(mList.get(position).name);
            viewHolder.mTextView2.setText(mList.get(position).cla);
            viewHolder.mTextView3.setText(mList.get(position).state);
            viewHolder.mTextView4.setText(mList.get(position).total);
            viewHolder.mTextView5.setText(mList.get(position).zhichen);
            return convertView;

        }

        class ViewHolder {
            private TextView mTextView1, mTextView2,mTextView3,mTextView4,mTextView5;
            private ImageView ivIcon;
        }
    }
}
