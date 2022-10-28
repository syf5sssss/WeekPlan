package com.work.weekplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.work.entity.Collect;
import com.work.exdao.ExCollectDao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private List<Collect> mData = null;
    private Context mContext;
    private CollectAdapter mAdapter = null;
    private ListView listView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectFragment newInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collect,container,false);
        mContext = getContext();
        listView = (ListView) view.findViewById(R.id.collect_listview);
        mData = new ArrayList<Collect>();
        mData = ExCollectDao.getInstance().SelCollectEnableOrderByUpdate(getContext());
        mAdapter = new CollectAdapter(mData, mContext);
        listView.setAdapter(mAdapter);

        //按钮
        view.findViewById(R.id.fab_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddCollectActivity.class));
            }
        });

        //删除与修改
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("","longClick id:"+id+"    position:"+position+"  UpdateId:"+mData.get(position).ID);
                Intent in = new Intent(new Intent(getContext(), EditCollectActivity.class));
                //2.传多个数据
                Bundle b = new Bundle();
                b.putLong("UpdateId", mData.get(position).ID);
                in.putExtras(b);
                startActivity(in);
                return true;
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        mData = new ArrayList<Collect>();
        mData = ExCollectDao.getInstance().SelCollectEnableOrderByUpdate(getContext());
        mAdapter = new CollectAdapter(mData, mContext);
        listView.setAdapter(mAdapter);
    }

}