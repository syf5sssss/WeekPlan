package com.work.weekplan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.work.Dto.Params;
import com.work.entity.Days;
import com.work.entity.Plans;
import com.work.entity.Projects;
import com.work.exdao.ExDaysDao;
import com.work.exdao.ExPlansDao;
import com.work.exdao.ExProjectsDao;
import com.work.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /**
     * 当前展示的日期
     */
    public Date show_Day = null;
    public long Day_Id = 0;
    EditText currentData;
    EditText planData;
    TextView text_days;
    TextView text_show_week;
    Button last_day;
    Button next_day;
    Button save_button;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ToDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDayFragment newInstance(String param1, String param2) {
        ToDayFragment fragment = new ToDayFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_day, container, false);
        currentData = view.findViewById(R.id.edit_current_data);
        planData = view.findViewById(R.id.edit_plan_data);
        text_days = view.findViewById(R.id.text_days);
        text_show_week = view.findViewById(R.id.text_show_week);
        last_day = view.findViewById(R.id.last_week);
        next_day = view.findViewById(R.id.next_day);
        save_button = view.findViewById(R.id.save_button);
        planData.setText("");
        currentData.setText("");

        //获取当前 日期 星期数，项目名称，计划内容，实际内容
        if (show_Day == null) {
            show_Day = new Date();
        }
        ReFlushView(text_show_week, planData, currentData, text_days);

        /**
         * 清空
         */
        view.findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentData.setText("");
            }
        });
        /**
         * 下一天
         */
        next_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateUtil.isSameDay(show_Day, new Date()) == 1) {
                    Toast.makeText(getContext(), "时间还没到", Toast.LENGTH_LONG).show();
                    return;
                }
                show_Day = DateUtil.GetNextDay(show_Day);
                ReFlushView(text_show_week, planData, currentData, text_days);
            }
        });

        /**
         * 前一天
         */
        last_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Day = DateUtil.GetLastDay(show_Day);
                ReFlushView(text_show_week, planData, currentData, text_days);
            }
        });

        /**
         * 保存
         */
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.getText().toString().equals("") || currentData.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "请填写当日内容", Toast.LENGTH_LONG).show();
                    return;
                }
                Days day = new Days();
                String week_num = DateUtil.GetYear_Week_Num(show_Day);
                List<Plans> pl_list = ExPlansDao.getInstance().SelPlansByWeekNum(getContext(), week_num);
                if (pl_list != null) {
                    for (int i = 0; i < pl_list.size(); i++) {
                        if (pl_list.get(i).EndTime.getYear() == show_Day.getYear()) {
                            if (pl_list.get(i).Data != null) {
                                if(Day_Id == 0){
                                    day.PlanId = Integer.parseInt(String.valueOf(pl_list.get(i).ID));
                                    day.Status = Params.Enable;
                                    day.UpdateTime = show_Day;
                                    day.CreateTime = new Date();
                                    day.Data = currentData.getText().toString().trim();
                                    Day_Id = ExDaysDao.getInstance().InsertDays(getContext(), day);
                                }else{
                                    day.ID = Day_Id;
                                    day.PlanId = Integer.parseInt(String.valueOf(pl_list.get(i).ID));
                                    day.Data = currentData.getText().toString().trim();
                                    ExDaysDao.getInstance().UpdateDays(getContext(), day);
                                }
                                Toast.makeText(getContext(), "提交成功", Toast.LENGTH_LONG).show();
                                return;
                            }
                            break;
                        }
                    }
                }
                Toast.makeText(getContext(), "没有计划", Toast.LENGTH_LONG).show();
                return;
            }
        });

        return view;
    }

    /**
     * 刷新当前界面控件
     */
    @SuppressLint("SetTextI18n")
    public void ReFlushView(TextView text_show_week, EditText planData, EditText currentData, TextView text_days) {
        planData.setText("");
        currentData.setText("");
        Day_Id = 0;

        //查询展示时间 所属周几 周数量
        Calendar cal = Calendar.getInstance();
        cal.setTime(show_Day);
        String week_num = DateUtil.GetYear_Week_Num(show_Day);
        int week = cal.get(Calendar.DAY_OF_WEEK);

        text_show_week.setText(getWeekString(week) + " " + week_num);

        List<Plans> pl_list = ExPlansDao.getInstance().SelPlansByWeekNum(getContext(), week_num);
        if (pl_list != null) {
            for (int i = 0; i < pl_list.size(); i++) {
                if (pl_list.get(i).EndTime.getYear() == show_Day.getYear()) {
                    if (pl_list.get(i).Data != null) {
                        planData.setText(pl_list.get(i).Data);
                    }
                    break;
                }
            }
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Days> list = ExDaysDao.getInstance().GetAllDays(getContext());
        String projectName = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (DateUtil.isSameDay(list.get(i).UpdateTime, show_Day) == 1) {
                    Plans pl = ExPlansDao.getInstance().SelPlansById(getContext(), list.get(i).PlanId);
//                    if(pl!=null && pl.Data!=null){
//                        planData.setText(pl.Data);
//                    }
                    Day_Id = list.get(i).ID;
                    if (list.get(i).Data != null) {
                        currentData.setText(list.get(i).Data);
                    }
                    if (pl != null && pl.ProjectId > 0) {
                        Projects pro = ExProjectsDao.getInstance().SelProjectsById(getContext(), list.get(i).PlanId);
                        if (pro != null && pro.Name != null) {
                            projectName = pro.Name;
                        }
                    }
                    break;
                }
            }

        }
        text_days.setText(sdf.format(show_Day) + " " + projectName);

    }

    @Override
    public void onResume() {
        super.onResume();
        ReFlushView(text_show_week, planData, currentData, text_days);
    }

    /**
     * 获取周描述
     */
    public String getWeekString(int num) {
        if (num == 1) {
            return "周日";
        }
        if (num == 2) {
            return "周一";
        }
        if (num == 3) {
            return "周二";
        }
        if (num == 4) {
            return "周三";
        }
        if (num == 5) {
            return "周四";
        }
        if (num == 6) {
            return "周五";
        }
        if (num == 7) {
            return "周六";
        }
        return "周?";
    }
}