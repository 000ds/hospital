package com.cyansoft.maodou_manger.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyansoft.maodou_manger.R;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */

public class OkFragment extends Fragment {
//    private ListView mListView;
//    private List<DataBean> mDataBeanList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.okfragment,container,false);
//        mListView = (ListView) view.findViewById(R.id.find_list);
//        findAdapter adapter= new findAdapter(this.getContext(),R.layout.finditem,mDataBeanList);
//        mListView.setAdapter(adapter);
        return view;
    }



}
