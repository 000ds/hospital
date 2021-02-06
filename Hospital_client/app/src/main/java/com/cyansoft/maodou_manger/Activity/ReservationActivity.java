package com.cyansoft.maodou_manger.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.JavaBean.Doctor;
import com.cyansoft.maodou_manger.JavaBean.Yuyue;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;
import com.cyansoft.maodou_manger.fragment.InfoFragment;
import com.demievil.library.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean isLogin = false;
    private ListView list_yuyue;
    private List<Yuyue> mList;
    //数据源
    private MyApplication myappication;
    private ImageView iBack;

    private List<Integer> list_money = new ArrayList<Integer>();
    int totalMoney = 0;
    private ProgressDialog rDialog;
    private RefreshLayout mRefreshLayout;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reservation);
        myappication = (MyApplication) getApplication();
        if (!myappication.isLogin) {
            Toast.makeText(ReservationActivity.this, "请登录后在查看预约订单!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ReservationActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        initview();
        JSONObject job=new JSONObject();
        try {
            job.put("flag","search");
            job.put("name",myappication.getUsername());
            getDataFromPost(job.toString());
        } catch (JSONException e) {
        }
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
        List<Yuyue> YuyueList = new ArrayList<>();
        JSONObject jsonobject;
        Yuyue yuyue;
        try {
//                jsonobject = new JSONObject(jsonString);
            JSONArray jsonarray = new JSONArray(res);
            for (int i = 0; i < jsonarray.length(); i++) {
                jsonobject = jsonarray.getJSONObject(i);
                yuyue = new Yuyue();

                yuyue.doctorname = jsonobject.getString("doctorname");
                yuyue.time = jsonobject.getString("time");
                yuyue.money = jsonobject.getString("money");
                yuyue.cla = jsonobject.getString("cla");
                yuyue.photo = jsonobject.getString("url");
                YuyueList.add(yuyue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(), YuyueList);
        list_yuyue.setAdapter(newsAdapter);
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
    private void initview() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.swipe_containe);
        list_yuyue = (ListView) findViewById(R.id.listview_yuyue);

        iBack = (ImageView) findViewById(R.id.i_back);

        iBack.setOnClickListener(this);

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        mRefreshLayout.setChildView(list_yuyue);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONObject job=new JSONObject();
                try {
                    job.put("flag","search");
                    job.put("name",myappication.getUsername());
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }

                mRefreshLayout.setRefreshing(false);

//                progressBar.setVisibility(View.GONE);
            }
        });
        list_yuyue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) list_yuyue.getChildAt(position).findViewById(R.id.item_checkbox);
                TextView state = (TextView) list_yuyue.getChildAt(position).findViewById(R.id.state);
                final TextView doctor = (TextView) list_yuyue.getChildAt(position).findViewById(R.id.item_textview_productname);
                ImageView delete = (ImageView) list_yuyue.getChildAt(position).findViewById(R.id.item_del);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
//                                rDialog = new ProgressDialog(getApplication());
//                                rDialog.setTitle("提示");
//                                rDialog.setMessage("正在提交,请稍后....");
//                                rDialog.setCancelable(true);
//                                rDialog.show();
                                String name = myappication.getUsername().toString();
                                String doctorname = doctor.getText().toString();

                            }
                        }).start();

                    }
                });

                checkBox.setChecked(true);
                int money = Integer.valueOf(mList.get(position).money);
                totalMoney += money;
                state.setText("success");

//                if (mList.get(position).state.equals("success")) {
//                    checkBox.setChecked(false);
//                    Toast.makeText(ReservationActivity.this, "已经预约成功!", Toast.LENGTH_SHORT).show();
//                } else {
//                    checkBox.setChecked(true);
//                    if (checkBox.isChecked()) {
//                        int money = Integer.valueOf(mList.get(position).money);
//                        totalMoney += money;
//                        state.setText("success");
//                    }
//                    total_money.setText("总价：￥" + totalMoney);

//                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.i_back:
                finish();
                break;
        }
    }


    /**
     * NewsAdapter是listview的适配器
     */
    public class NewsAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        public NewsAdapter(Context context, List<Yuyue> data) {
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
                convertView = mLayoutInflater.inflate(R.layout.item_reservation, null);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.mDoctorname = (TextView) convertView.findViewById(R.id.item_textview_productname);
                viewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
                viewHolder.Money = (TextView) convertView.findViewById(R.id.item_text_price);
                viewHolder.State = (TextView) convertView.findViewById(R.id.state);
                viewHolder.Cla = (TextView) convertView.findViewById(R.id.cla);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String url = mList.get(position).photo;
            x.image().bind(viewHolder.ivIcon, url);
            viewHolder.mDoctorname.setText(mList.get(position).doctorname);
            viewHolder.mTime.setText("时间:" + mList.get(position).time);
            viewHolder.Money.setText(mList.get(position).money);
            viewHolder.Cla.setText("科室:" + mList.get(position).cla);
            viewHolder.State.setText(mList.get(position).state);

            return convertView;

        }

        class ViewHolder {
            private TextView mDoctorname, mTime, Money, Cla, State;
            private ImageView ivIcon;
        }
    }
}
