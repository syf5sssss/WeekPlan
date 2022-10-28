package com.work.weekplan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.work.Dto.Item;

import java.util.List;

public class PlanDetailsAdapter extends BaseAdapter {
    private List<Item> mData;
    private Context mContext;

    public PlanDetailsAdapter(List<Item> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if(mData == null || mData.size() ==0){
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolderItem holder1 = null;//未完成
            if (convertView == null || holder1 == null) {
                Log.e("convertView"," == null");
                if(holder1 == null)
                {
                    holder1 = new ViewHolderItem();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exlist_item_details
                            , parent
                            , false);
                    holder1.tv_data = (TextView) convertView.findViewById(R.id.tv_data_details);
                    holder1.tv_date = (TextView) convertView.findViewById(R.id.tv_date_details);
                    holder1.tv_plan = (TextView) convertView.findViewById(R.id.tv_plan_details);
                }
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolderItem) convertView.getTag();
            }
            Log.e("","position:"+position);
//            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
            //已完成
//            holder1.tv_date.setText(sdf.format(mData.get(position).getiDate()));
            holder1.tv_date.setText(mData.get(position).getiDate());
            holder1.tv_data.setText(mData.get(position).getiData());
            holder1.tv_plan.setText(mData.get(position).getiPlan());
            Log.e("","Enable data:"+mData.get(position).getiPlan());
        } catch (Exception e) {
            Log.e("","getView:"+e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }


    private static class ViewHolderItem {
        private TextView tv_plan;
        private TextView tv_data;
        private TextView tv_date;
    }


}
