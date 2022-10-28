package com.work.weekplan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.work.Dto.Group;
import com.work.Dto.Item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MExpandableListAdapter myAdapter = null;


    public PlanDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanDetailsFragment newInstance(String param1, String param2) {
        PlanDetailsFragment fragment = new PlanDetailsFragment();
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
        View view = inflater.inflate(R.layout.item_exlist_item_details,container,false);
        try {
            mContext = getContext();
            exlist_lol = view.findViewById(R.id.expandable_list_view);
            exlist_lol.setGroupIndicator(null);
            //数据准备
            gData = new ArrayList<Group>();
            iData = new ArrayList<ArrayList<Item>>();
            gData.add(new Group("树莓派","2021-10-25","3",1l));
            gData.add(new Group("App","2021-10-25","2",2l));
            gData.add(new Group("插画","2021-10-25","4",3l));

            lData = new ArrayList<Item>();

            //树莓派组
            lData.add(new Item("创建计划", "","",0l));
            lData.add(new Item("36/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("35/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("34/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            iData.add(lData);
            //App组
            lData = new ArrayList<Item>();
            lData.add(new Item("创建计划", "","",0l));
            lData.add(new Item("33/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("32/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            iData.add(lData);
            //插画
            lData = new ArrayList<Item>();
            lData.add(new Item("创建计划", "","",0l));
            lData.add(new Item("31/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("30/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("29/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            lData.add(new Item("28/52", "本周计划重点\n1.XXX\n2.XXX","2021-10-25",0l));
            iData.add(lData);

            myAdapter = new MExpandableListAdapter(gData, iData, mContext);
            exlist_lol.setAdapter(myAdapter);
        } catch (Exception e) {
            Log.e("",e.getMessage());
            Log.e("",e.getStackTrace().toString());
            e.printStackTrace();
        }

        return view;
    }
}