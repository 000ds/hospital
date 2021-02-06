package com.cyansoft.maodou_manger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyansoft.maodou_manger.JavaBean.BaeBean;
import com.cyansoft.maodou_manger.JavaBean.DataBean;
import com.cyansoft.maodou_manger.R;

import java.util.List;

/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class findAdapter extends ArrayAdapter<BaeBean> {
    private int resourceId;
    public findAdapter(Context context, int resource, List<BaeBean> objects) {

        super(context, resource, objects);
        this.resourceId = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        viewHolder= new viewHolder();
        View view;
        BaeBean baeBean = getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId ,null);
            viewHolder.sName = (TextView) view.findViewById(R.id.s_name);
            viewHolder.sComment = (TextView) view.findViewById(R.id.s_price);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (viewHolder) view.getTag();
        }
        viewHolder.sName.setText(baeBean.getName());
        viewHolder.sComment.setText(baeBean.getComment());
        return view;
    }
    class viewHolder{
        private TextView sName;
        private TextView sComment;
    }
}
