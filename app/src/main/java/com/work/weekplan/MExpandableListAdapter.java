package com.work.weekplan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.work.Dto.Group;
import com.work.Dto.Item;
import com.work.weekplan.slide.RecyclerAdapter;

import java.util.ArrayList;


public class MExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Item>> iData;
    private Context mContext;

    public MExpandableListAdapter(ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(iData ==null || iData.size() ==0){
            return 0;
        }
        if(iData.get(groupPosition) == null){
            return 0;
        }
        return iData.get(groupPosition).size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        try {
            ViewHolderGroup groupHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.item_exlist_group, parent, false);
                groupHolder = new ViewHolderGroup();
                groupHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                groupHolder.tv_last_update = (TextView) convertView.findViewById(R.id.tv_last_update);
                groupHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (ViewHolderGroup) convertView.getTag();
            }
            groupHolder.tv_title.setText(gData.get(groupPosition).getgTitle());
            groupHolder.tv_last_update.setText(gData.get(groupPosition).getgLastUpdate());
            groupHolder.tv_number.setText(gData.get(groupPosition).getgNumber());
        } catch (Exception e) {
            Log.e("",e.getMessage());
            Log.e("",e.getStackTrace().toString());
            e.printStackTrace();
        }
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @SuppressLint("ResourceType")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {
            ViewHolderItem0 itemHolder0;
            ViewHolderItem itemHolder;
            Log.e("","childPosition:"+childPosition+"   isLastChild:"+isLastChild);
            if(childPosition == 0){//三个按钮
                if (convertView == null || (convertView.getId()!=R.layout.item_exlist_item_plan0)) {
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.item_exlist_item_plan0, parent, false);
                    itemHolder0 = new ViewHolderItem0();
                    itemHolder0.edit_button = (Button) convertView.findViewById(R.id.update_button_project);
                    itemHolder0.add_button = (Button) convertView.findViewById(R.id.add_button_plan);
                    convertView.setTag(itemHolder0);

                    itemHolder0.add_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //添加计划
                            ProjectsFragment.OpenAddPlanView(groupPosition);
                        }
                    });
                    itemHolder0.edit_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //调整项目
                            ProjectsFragment.OpenEditProjectView(groupPosition);
                        }
                    });

                } else {
                    itemHolder0 = (ViewHolderItem0) convertView.getTag();
                }
            }else{//真实内容
                if (convertView == null || (convertView.getId() != R.layout.item_exlist_item_plan)) {
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.item_exlist_item_plan, parent, false);
                    itemHolder = new ViewHolderItem();
                    itemHolder.tv_plan = (TextView) convertView.findViewById(R.id.tv_number_plan);
                    itemHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data_plan);
                    itemHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date_plan);
                    convertView.setTag(itemHolder);
                } else {
                    itemHolder = (ViewHolderItem) convertView.getTag();
                }
                itemHolder.tv_plan.setText(iData.get(groupPosition).get(childPosition).getiPlan());
                itemHolder.tv_data.setText(iData.get(groupPosition).get(childPosition).getiData());
                itemHolder.tv_date.setText(iData.get(groupPosition).get(childPosition).getiDate());
            }
        } catch (Exception e) {
            Log.e("",e.getMessage());
            Log.e("",e.getStackTrace().toString());
            e.printStackTrace();
        }
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class ViewHolderGroup {
        private TextView tv_title;
        private TextView tv_last_update;
        private TextView tv_number;
    }

    private static class ViewHolderItem {
        private TextView tv_plan;
        private TextView tv_data;
        private TextView tv_date;
    }

    private static class ViewHolderItem0 {
        private Button add_button;
        private Button edit_button;
    }

}

