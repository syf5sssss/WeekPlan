package com.work.weekplan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.work.entity.Events;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventsAdapter extends BaseAdapter {

    private List<Events> mData;
    private Context mContext;
    //定义两个类别标志
    private static final int TYPE_NEWS_1 = 0;
    private static final int TYPE_NEWS_2 = 1;

    public EventsAdapter(List<Events> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            String str = mData.get(position).Type;//获取该事件类型
//            Log.e("","position:"+position+"  type:"+str);
            ViewHolder holder1 = null;//未完成
            ViewHolder2 holder2 = null;//已完成
            if (convertView == null || holder2 == null || holder1 == null) {
//                Log.e("convertView"," == null");
                if(str.equals("Enable"))
                {   //已完成
                    if(holder2 == null)
                    {
                        holder2 = new ViewHolder2();
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_listview_events_ok
                                , parent
                                , false);
                        holder2.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                        holder2.title = (TextView) convertView.findViewById(R.id.tv_title);
                        holder2.content = (TextView) convertView.findViewById(R.id.tv_content);
                    }
                    convertView.setTag(holder2);
                } else {
                    //未完成
                    if(holder1 == null)
                    {
                        holder1 = new ViewHolder();
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_listview_events_ng
                                , parent
                                , false);
                        holder1.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                        holder1.title = (TextView) convertView.findViewById(R.id.tv_title);
                        holder1.content = (TextView) convertView.findViewById(R.id.tv_content);
                    }
                    convertView.setTag(holder1);
                }
            } else {
//                Log.e("convertView"," != null");
                if(str.equals("Enable"))
                {   //已完成
                    holder2 = (ViewHolder2) convertView.getTag();
                } else {
                    //未完成
                    holder1 = (ViewHolder) convertView.getTag();
                }
            }
//            Log.e("","position:"+position);
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
            if(str.equals("Enable"))
            {   //已完成
                holder2.title.setText(sdf.format(mData.get(position).getCreateTime()));
                holder2.content.setText(mData.get(position).getData());
//                Log.e("","Enable data:"+mData.get(position).getData());
            } else {
                //未完成
                holder1.title.setText(sdf.format(mData.get(position).getCreateTime()));
                holder1.content.setText(mData.get(position).getData());
//                Log.e("","Disable data:"+mData.get(position).getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    /**
     * 展示 未完成 事件
     */
    static class ViewHolder {
        ImageView img_icon;
        TextView title;
        TextView content;
    }

    /**
     * 展示 已完成 事件
     */
    static class ViewHolder2 {
        ImageView img_icon;
        TextView title;
        TextView content;
    }
}
