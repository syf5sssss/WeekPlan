package com.work.weekplan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.work.Dto.Params;
import com.work.entity.Events;
import com.work.Dto.SwipeMenu;
import com.work.Dto.SwipeMenuItem;
import com.work.exdao.ExCollectDao;
import com.work.exdao.ExEventsDao;
import com.work.util.DbController;
import com.work.view.SwipeMenuView;
import com.work.weekplan.slide.DividerItemDecoration;
import com.work.weekplan.slide.RecyclerAdapter;
import com.work.weekplan.slide.SwapRecyclerView;
import com.work.weekplan.slide.SwipeMenuBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment implements SwipeMenuBuilder {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private RecyclerAdapter adapter;
    private SwapRecyclerView recyclerView;
    private int pos;

    private List<Events> mData = null;
    private Context mContext;
    private ListView listView;
    private FloatingActionButton fab = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventsFragment() {
        // Required empty public constructor
    }

    private static EventsFragment eventsFragment;

    /**
     * 获取单例
     */
    public static EventsFragment getInstance(){
        if(eventsFragment == null){
            synchronized (EventsFragment.class){
                if(eventsFragment == null){
                    eventsFragment = new EventsFragment();
                }
            }
        }
        return eventsFragment;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        eventsFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events,container,false);
        mContext = view.getContext();
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setRippleColor(Color.parseColor("#FFFFFF"));
        mData = new ArrayList<Events>();
        mData = ExEventsDao.getInstance().SelEventsEnableOrderByUpdate(getContext());
        recyclerView = (SwapRecyclerView) view.findViewById(R.id.id_rv);
        adapter = new RecyclerAdapter<Events>(mData, getActivity(),this);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnSwipeListener(new SwapRecyclerView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
//                Toast.makeText(MainActivity.this,"onSwipeStart-"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeEnd(int position) {
//                Toast.makeText(MainActivity.this, "onSwipeEnd-" + position, Toast.LENGTH_SHORT).show();
                pos = position;
            }
        });

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            /**
             * 短按
             */
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
//                Toast.makeText(getActivity(), "adapter.onItemClick-->>>"+mData.get(position).getData(), Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "点击了第" + position + "条数据", Toast.LENGTH_SHORT).show();
            }
            /**
             * 长按
             */
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                //打开事件详情
                Toast.makeText(getActivity(), "onItemLongClick-->>>"+mData.get(position).getData(), Toast.LENGTH_LONG).show();
                Intent in = new Intent(new Intent(getContext(), EditEventActivity.class));
                //2.传多个数据
                Bundle b = new Bundle();
                b.putLong("UpdateId", mData.get(position).ID);
                in.putExtras(b);
                startActivity(in);
                //startActivity(new Intent(getContext(), EditEventActivity.class));
                return true;
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开事件详情
                startActivity(new Intent(getContext(), AddEventActivity.class));
            }
        });

        return view;
    }
    /**
     * 点击之后，恢复原来样式
     */
    private SwipeMenuView.OnMenuItemClickListener mOnSwipeItemClickListener = new SwipeMenuView.OnMenuItemClickListener() {

        @Override
        public void onMenuItemClick(int pos, SwipeMenu menu, int index) {
//            Toast.makeText(getContext(), menu.getMenuItem(index).getTitle(), Toast.LENGTH_LONG).show();
            if(menu.getMenuItem(index).getTitle().toString().equals("启动")){
                //点击已完成
                Events ev= new Events();
                ev.ID = mData.get(pos).ID;
                ev.Type = Params.Disable;
                ev.UpdateTime = new Date();
                ExEventsDao.getInstance().UpdateEvents(getContext(), ev);
            }
            if(menu.getMenuItem(index).getTitle().toString().equals("完成")){
                //点击未完成
                Events ev= new Events();
                ev.ID = mData.get(pos).ID;
                ev.Type = Params.Enable;
                ev.UpdateTime = new Date();
                ExEventsDao.getInstance().UpdateEvents(getContext(), ev);
            }

            if (index == 0) {
                recyclerView.smoothCloseMenu(pos);
//                mData.remove(pos);
//                adapter.remove(pos);
            }

//            onResume();
        }
    };
    /**
     * 创建左滑OK菜单
     */
    @Override
    public SwipeMenuView createOK() {
        SwipeMenu menu = new SwipeMenu(getActivity());
        SwipeMenuItem item;
        item = new SwipeMenuItem(getActivity());
        item.setTitle("启动")
                .setTitleColor(Color.WHITE)
                .setBackground(new ColorDrawable(Color.parseColor("#FF9800")))
                .setWidth(dp2px(80))
                .setTitleSize(20);
//                .setIcon(android.R.drawable.sym_def_app_icon);
        menu.addMenuItem(item);

        SwipeMenuView menuView = new SwipeMenuView(menu);

        menuView.setOnMenuItemClickListener(mOnSwipeItemClickListener);

        return menuView;
    }
    /**
     * 创建左滑NG菜单
     */
    public SwipeMenuView createNG() {
        SwipeMenu menu = new SwipeMenu(getActivity());
        SwipeMenuItem item;
        item = new SwipeMenuItem(getActivity());
        item.setTitle("完成")
                .setTitleColor(Color.WHITE)
                .setBackground(new ColorDrawable(Color.parseColor("#43BF3E")))
                .setWidth(dp2px(80))
                .setTitleSize(20);
//                .setIcon(android.R.drawable.sym_def_app_icon);
        menu.addMenuItem(item);

        SwipeMenuView menuView = new SwipeMenuView(menu);

        menuView.setOnMenuItemClickListener(mOnSwipeItemClickListener);

        return menuView;
    }
    /**
     * 设置列宽
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    /**
     * 重新加载页面
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.e("","重新加载EventsFragment");
        mData = new ArrayList<Events>();
        mData = ExEventsDao.getInstance().SelEventsEnableOrderByUpdate(getContext());
        adapter = new RecyclerAdapter<Events>(mData, getActivity(),this);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            /**
             * 短按
             */
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
//                Toast.makeText(getActivity(), "adapter.onItemClick-->>>"+mData.get(position).getData(), Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "点击了第" + position + "条数据", Toast.LENGTH_SHORT).show();
            }
            /**
             * 长按
             */
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                //打开事件详情
                Intent in = new Intent(new Intent(getContext(), EditEventActivity.class));
                //2.传多个数据
                Bundle b = new Bundle();
                b.putLong("UpdateId", mData.get(position).ID);
                in.putExtras(b);
                startActivity(in);
                return true;
            }

        });
    }



}