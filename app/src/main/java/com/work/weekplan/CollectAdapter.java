package com.work.weekplan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.work.entity.Collect;

import java.util.List;

public class CollectAdapter extends BaseAdapter {

    private List<Collect> mData;
    private Context mContext;
    private ViewHolder holder1 = null;

    public CollectAdapter(List<Collect> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if(mData == null)
        {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null || holder1 == null) {
                holder1 = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_collect
                        , parent
                        , false);
                holder1.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder1.contents = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolder) convertView.getTag();
            }
            holder1.contents.setText(mData.get(position).getContents()+" \n");
            holder1.name.setText(mData.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


    static class ViewHolder {
        TextView name;
        TextView contents;
    }


}
