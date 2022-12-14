package com.work.weekplan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.work.Dto.Group;
import com.work.Dto.Item;
import com.work.entity.Plans;
import com.work.entity.Projects;
import com.work.exdao.ExPlansDao;
import com.work.exdao.ExProjectsDao;
import com.work.weekplan.slide.EditProjectActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MExpandableListAdapter myAdapter = null;
    public static boolean isLongClick = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectsFragment newInstance(String param1, String param2) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        projectsFragment = fragment;
        return fragment;
    }


    private static ProjectsFragment projectsFragment;

    /**
     * ????????????
     */
    public static ProjectsFragment getInstance(){
        if(projectsFragment == null){
            synchronized (ProjectsFragment.class){
                if(projectsFragment == null){
                    projectsFragment = new ProjectsFragment();
                }
            }
        }
        return projectsFragment;
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
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        try {
            mContext = getContext();
            exlist_lol = view.findViewById(R.id.expandable_list_view);
            exlist_lol.setGroupIndicator(null);
            //????????????
            gData = new ArrayList<Group>();
            iData = new ArrayList<ArrayList<Item>>();

            List<Projects> list_group = ExProjectsDao.getInstance().SelProjectsEnableOrderByUpdate(getContext());
            if (list_group != null && list_group.size() > 0) {
                //????????????
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < list_group.size(); i++) {
                    //????????????id??????????????????
                    List<Plans> plan_list = ExPlansDao.getInstance().SelPlansByProjectId(getContext(), list_group.get(i).ID);
                    int plan_num = plan_list == null ? 0 : plan_list.size();
                    lData = new ArrayList<Item>();
                    lData.add(new Item("????????????", "","",0l));
                    if(plan_num>0){
                        for(int y =0;y<plan_num;y++){
                            lData.add(new Item(plan_list.get(y).WeekNum+"\n", plan_list.get(y).Data+"\n",sdf.format(plan_list.get(y).CreateTime),plan_list.get(y).ID));
                        }
                    }
                    gData.add(new Group(list_group.get(i).Name+"\n", sdf.format(list_group.get(i).UpdateTime), (lData.size()-1)+"", list_group.get(i).ID));
                    iData.add(lData);
                }
            }
            //???????????????
//            gData.add(new Group("?????????\n","2021-10-25","3"));
//            gData.add(new Group("App\n","2021-10-25","2"));
//            gData.add(new Group("??????\n","2021-10-25","4"));
//
//            lData = new ArrayList<Item>();
//
//            //????????????
//            lData.add(new Item("????????????", "",""));
//            lData.add(new Item("36/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("35/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("34/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            iData.add(lData);
//            //App???
//            lData = new ArrayList<Item>();
//            lData.add(new Item("????????????", "",""));
//            lData.add(new Item("33/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("32/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            iData.add(lData);
//            //??????
//            lData = new ArrayList<Item>();
//            lData.add(new Item("????????????", "",""));
//            lData.add(new Item("31/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("30/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("29/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            lData.add(new Item("28/52", "??????????????????\n1.XXX\n2.XXX","2021-10-25"));
//            iData.add(lData);

            myAdapter = new MExpandableListAdapter(gData, iData, mContext);

            exlist_lol.setAdapter(myAdapter);

            //???????????????????????????
            exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                    Toast.makeText(mContext, "???????????????" + iData.get(groupPosition).get(childPosition).getiPlan(), Toast.LENGTH_SHORT).show();
                    String str = iData.get(groupPosition).get(childPosition).getiData();
                    if (str == null || str.isEmpty()) {
                        //????????????????????????
//                        startActivity(new Intent(getContext(), AddPlanActivity.class));
                    } else {
                        //????????????????????????
                        startActivity(new Intent(getContext(), PlanDetailsActivity.class));
                    }
                    return true;
                }

            });
            //??????
            view.findViewById(R.id.fab_project).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AddProjectActivity.class));
                }
            });


        } catch (Exception e) {
            Log.e("", e.getMessage());
            Log.e("", e.getStackTrace().toString());
            e.printStackTrace();
        }
        return view;
    }


    /**
     * ????????????????????????
     */
    public static void OpenAddPlanView(int projectId){
        Log.e("","OpenAddPlanView-projectId:"+projectId);
        long id = projectsFragment.gData.get(projectId).ID;
        Intent in = new Intent(new Intent(projectsFragment.getContext(), AddPlanActivity.class));
        //2.???????????????
        Bundle b = new Bundle();
        b.putLong("ProjectId", id);
        in.putExtras(b);
        projectsFragment.startActivity(in);
//        projectsFragment.startActivity(new Intent(projectsFragment.getContext(), AddPlanActivity.class));
    }

    /**
     * ????????????????????????
     */
    public static void OpenEditProjectView(int projectId){
        Log.e("","OpenEditProjectView-projectId:"+projectId);
        long id = projectsFragment.gData.get(projectId).ID;
        Intent in = new Intent(new Intent(projectsFragment.getContext(), EditProjectActivity.class));
        //2.???????????????
        Bundle b = new Bundle();
        b.putLong("ProjectId", id);
        in.putExtras(b);
        projectsFragment.startActivity(in);
//        projectsFragment.startActivity(new Intent(projectsFragment.getContext(), AddPlanActivity.class));
    }
    
    @Override
    public void onResume() {
        super.onResume();

        exlist_lol.setGroupIndicator(null);
        //????????????
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();

        List<Projects> list_group = ExProjectsDao.getInstance().SelProjectsEnableOrderByUpdate(getContext());
        if (list_group != null && list_group.size() > 0) {
            //????????????
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < list_group.size(); i++) {
                //????????????id??????????????????
                List<Plans> plan_list = ExPlansDao.getInstance().SelPlansByProjectId(getContext(), list_group.get(i).ID);
                int plan_num = plan_list == null ? 0 : plan_list.size();
                lData = new ArrayList<Item>();
                lData.add(new Item("????????????", "","",0l));
                if(plan_num>0){
                    for(int y =0;y<plan_num;y++){
                        lData.add(new Item(plan_list.get(y).WeekNum+"\n", plan_list.get(y).Data,sdf.format(plan_list.get(y).CreateTime),plan_list.get(y).ID));
                    }
                }
                gData.add(new Group(list_group.get(i).Name+"\n", sdf.format(list_group.get(i).UpdateTime), (lData.size()-1)+"", list_group.get(i).ID));
                iData.add(lData);
            }
        }

        myAdapter = new MExpandableListAdapter(gData, iData, mContext);
        exlist_lol.setAdapter(myAdapter);

        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ArrayList<Item> temp_list = new ArrayList<>();
                temp_list = iData.get(groupPosition);
                if(isLongClick){
                    //?????? ??????????????????
                    Intent in = new Intent(new Intent(getContext(), EditPlanActivity.class));
                    //2.???????????????
                    Bundle b = new Bundle();
                    b.putLong("UpdateId", temp_list.get(childPosition).getId());
                    b.putString("UpdateData", temp_list.get(childPosition).getiData());
                    b.putString("UpdateWeekNum", temp_list.get(childPosition).getiPlan());
                    in.putExtras(b);
                    startActivity(in);
                }else{
                    //?????? ??????????????????
                    Intent in = new Intent(new Intent(getContext(), PlanDetailsActivity.class));
                    //2.???????????????
                    Bundle b = new Bundle();
                    if(temp_list.size() == 1)
                    {
                        b.putLong("PlansId", -1);
                        b.putString("PlansData", "");
                        b.putString("PlansWeekNum", "");
                    }else{
                        b.putLong("PlansId", temp_list.get(childPosition).getId());
                        b.putString("PlansData", temp_list.get(childPosition).getiData());
                        b.putString("PlansWeekNum", temp_list.get(childPosition).getiPlan());
                    }
                    in.putExtras(b);
                    startActivity(in);
//                    startActivity(new Intent(getContext(), PlanDetailsActivity.class));
                }
                isLongClick = false;
                return true;
            }
        });


        exlist_lol.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                isLongClick = true;
                return false;
            }
        });

    }
}