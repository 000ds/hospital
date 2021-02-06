package com.cyansoft.maodou_manger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyansoft.maodou_manger.JavaBean.DataBean;
import com.cyansoft.maodou_manger.R;

import java.util.List;

/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class Adapter extends ArrayAdapter<DataBean> {
  private int resourceId;

    public Adapter(Context context, int resource, List<DataBean> objects) {
        super(context, resource, objects);
        this.resourceId = resource;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
                viewHolder= new viewHolder();
        View view;
        DataBean dataBean = getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId ,null);
            viewHolder.t_name = (TextView) view.findViewById(R.id.feilei_list_text);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (viewHolder) view.getTag();
        }
        viewHolder.t_name.setText(dataBean.getName());


        return view;
    }
    class viewHolder{
        private TextView t_name;
    }

}
