package com.cyansoft.maodou_manger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.Activity.DoctorDetailsActivity;
import com.cyansoft.maodou_manger.Activity.FirstActivity;
import com.cyansoft.maodou_manger.Activity.LoginActivity;
import com.cyansoft.maodou_manger.Activity.MyApplication;
import com.cyansoft.maodou_manger.Activity.SuosuoActivity;
import com.cyansoft.maodou_manger.JavaBean.Doctor;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;
import com.demievil.library.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class InfoFragment extends Fragment {

    private TextView sousuoText;
    private EditText sousuoContent;
    private ListView list_info;
    private List<Doctor> mList;
    private LinearLayout sousuo_LinearLayout;
    private RefreshLayout mRefreshLayout;
    private Handler mHandler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        initview(view);
        JSONObject job=new JSONObject();
        try {
            job.put("flag","search");
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
        return view;


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
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), doctorList);
        list_info.setAdapter(newsAdapter);
    }
    private void initview(View view) {

        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_co);
        sousuoContent = (EditText) view.findViewById(R.id.sousuo_edittxt);
        list_info = (ListView) view.findViewById(R.id.list_user);
        sousuo_LinearLayout = (LinearLayout) view.findViewById(R.id.sousuo_LinearLayout);
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        mRefreshLayout.setChildView(list_info);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONObject job=new JSONObject();
                try {
                    job.put("flag","search");
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }
                mRefreshLayout.setRefreshing(false);
//                progressBar.setVisibility(View.GONE);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                list_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), DoctorDetailsActivity.class);
                        Bundle bd = new Bundle();
                        bd.putString("name", mList.get(position).name);
                        bd.putString("class", mList.get(position).cla);
                        bd.putString("phone", mList.get(position).phone);
                        bd.putString("zhicheng", mList.get(position).zhichen);
                        bd.putString("resume", mList.get(position).resume);
                        bd.putString("info", mList.get(position).info);
                        bd.putString("special", mList.get(position).special);
                        bd.putString("photo", mList.get(position).getPhoto());
                        bd.putString("money",mList.get(position).money);
                        bd.putString("state",mList.get(position).state);
                        bd.putString("total",mList.get(position).total);
                        intent.putExtras(bd);
                        startActivity(intent);
                    }
                });
            }
        }).start();
        sousuo_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), SuosuoActivity.class);
                startActivity(in);
            }
        });
        sousuoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), SuosuoActivity.class);
                startActivity(in);
            }
        });

    }
    private void getDataFromPost(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.DOCTOR_URL, json);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
                viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.dstate);
                viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.user_class);
                viewHolder.mTextView4= (TextView) convertView.findViewById(R.id.total);
                viewHolder.mTextView5 = (TextView) convertView.findViewById(R.id.user_zhicheng);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String url = mList.get(position).photo;
            x.image().bind(viewHolder.ivIcon, url);
            viewHolder.mTextView1.setText(mList.get(position).name);
            viewHolder.mTextView2.setText(mList.get(position).cla);
            viewHolder.mTextView3.setText(mList.get(position).resume);
            viewHolder.mTextView4.setText(mList.get(position).special);
            viewHolder.mTextView5.setText(mList.get(position).zhichen);

            return convertView;

        }

        class ViewHolder {
            private TextView mTextView1, mTextView2,mTextView3,mTextView4,mTextView5;
            private ImageView ivIcon;
        }
    }
}
