package com.cyansoft.maodou_manger.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.cyansoft.maodou_manger.Adapter.findAdapter;
import com.cyansoft.maodou_manger.JavaBean.BaeBean;
import com.cyansoft.maodou_manger.JavaBean.DataBean;
import com.cyansoft.maodou_manger.JavaBean.Doctor;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class FindFragment extends Fragment {
    private ListView mListView;
    private List<BaeBean> mDataBeanList = new ArrayList<>();
    private Handler mHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_fragment,container,false);
        mListView = (ListView) view.findViewById(R.id.find_list);
        findAdapter adapter= new findAdapter(getActivity(),R.layout.finditem2,mDataBeanList);
        mListView.setAdapter(adapter);
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
        findAdapter adapter= new findAdapter(getActivity(),R.layout.finditem,mDataBeanList);
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
}
