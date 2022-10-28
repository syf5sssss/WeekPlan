package com.work.weekplan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.work.Dto.QNumberPicker;
import com.work.dao.DaoMaster;
import com.work.entity.Collect;
import com.work.entity.Days;
import com.work.entity.Events;
import com.work.entity.Plans;
import com.work.entity.Projects;
import com.work.exdao.ExCollectDao;
import com.work.exdao.ExDaysDao;
import com.work.exdao.ExEventsDao;
import com.work.exdao.ExPlansDao;
import com.work.exdao.ExProjectsDao;
import com.work.util.ColourUtil;
import com.work.util.DateUtil;
import com.work.util.ZzExcelCreator;
import com.work.util.ZzFormatCreator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class SettingsFragment extends Fragment implements MainActivity.ActivityListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    private static SettingsFragment settingsFragment;

    /**
     * 获取单例
     */
    public static SettingsFragment getInstance() {
        if (settingsFragment == null) {
            synchronized (SettingsFragment.class) {
                if (settingsFragment == null) {
                    settingsFragment = new SettingsFragment();
                }
            }
        }
        return settingsFragment;
    }


    /**
     * Excel保存路径
     */
    private static String PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS) + "/week_plan/";
    //    private static String PATH = "/storage/emulated/0/documents/week_plan/";
    private static final String file_name = "week_plan";
    String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        settingsFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button export_excel = view.findViewById(R.id.export_excel);
        Button button_sync_data = view.findViewById(R.id.button_sync_data);
//        Button export_year_excel_button = view.findViewById(R.id.export_year_excel_button);
//
//        QNumberPicker numberPicker = view.findViewById(R.id.year_picker);
//        String[] numbers = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
//        //设置需要显示的内容数组
//        numberPicker.setDisplayedValues(numbers);
//        //设置最大最小值
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(numbers.length);
//        //设置默认的位置
//        numberPicker.setValue(1);
//        //这里设置为不循环显示，默认值为true
//        numberPicker.setWrapSelectorWheel(false);
//        //设置不可编辑
//        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        setNumberPickerDividerColor(numberPicker);
//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //得到选择结果
//                Log.e("", "oldVal:" + oldVal + "   newVal:" + newVal);
//            }
//        });


        //导出表格
        export_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断是否需要动态获取权限
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    int permission0 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                    int permission1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permission0 != PackageManager.PERMISSION_GRANTED || permission1 != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(permissions, 100);
                    }
//
//                    if(permission1 != PackageManager.PERMISSION_GRANTED){
//                        requestPermissions(permissions,200);
//                    }
                    if (permission1 == PackageManager.PERMISSION_GRANTED && permission0 == PackageManager.PERMISSION_GRANTED) {
                        ExcelOut();
                    }
                } else {
                    ExcelOut();
                }
            }
        });

        //同步数据
        button_sync_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否需要动态获取权限
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    int permission0 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                    int permission1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permission0 != PackageManager.PERMISSION_GRANTED || permission1 != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(permissions, 100);
                    }
//
//                    if(permission1 != PackageManager.PERMISSION_GRANTED){
//                        requestPermissions(permissions,200);
//                    }
                    if (permission1 == PackageManager.PERMISSION_GRANTED && permission0 == PackageManager.PERMISSION_GRANTED) {
//                        OpenFileManagement();
                        SyncData(PATH + file_name + ".xls");
                    }
                } else {
                    SyncData(PATH + file_name + ".xls");
//                    OpenFileManagement();//这里获取的路径总是不对，搞不清楚
                }
            }
        });

        //整年记录
//        export_year_excel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = numbers[numberPicker.getValue() - 1];
//                Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    /**
     * 打开文件选择器
     */
    public void OpenFileManagement() {
        //获取选择文件路径
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 114);
    }

    /**
     * 同步数据
     */
    public void SyncData(String get_file_path) {

        ZzExcelCreator collect_sheet = null;
        ZzExcelCreator events_sheet = null;
        ZzExcelCreator plans_sheet = null;
        ZzExcelCreator projects_sheet = null;
        ZzExcelCreator days_sheet = null;
        List<Collect> collect_list = new ArrayList<>();
        List<Days> days_list = new ArrayList<>();
        List<Events> events_list = new ArrayList<>();
        List<Plans> plans_list = new ArrayList<>();
        List<Projects> projects_list = new ArrayList<>();
        try {
//            Toast.makeText(getContext(), get_file_path, Toast.LENGTH_SHORT).show();
            System.out.println("get_file_path:" + get_file_path);
            //Collect
            collect_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(get_file_path))
                    .openSheet(0);//打开第1个sheet Collect
            int collect_rows = collect_sheet.getWritableSheet().getRows();
            int collect_cols = collect_sheet.getWritableSheet().getColumns();
            if (collect_cols != 6) {
                Toast.makeText(getContext(), "collect 数据不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            if (collect_rows > 1) {

                for (int i = 0; i < collect_rows-1; i++) {
                    Collect col = new Collect();
                    col.setID(Long.parseLong(collect_sheet.getCellContent(0, i+1)));
                    col.setContents(collect_sheet.getCellContent(1, i+1));
                    col.setName(collect_sheet.getCellContent(2, i+1));
                    col.setCreateTime(DateUtil.getDateFromString(collect_sheet.getCellContent(3, i+1)));
                    col.setUpdateTime(DateUtil.getDateFromString(collect_sheet.getCellContent(4, i+1)));
                    col.setStatus(collect_sheet.getCellContent(5, i+1));
                    collect_list.add(col);
//                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }

            }
            collect_sheet.close();
            //Day
            days_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(get_file_path))
                    .openSheet(1);//打开第2个sheet Days

            int days_rows = days_sheet.getWritableSheet().getRows();
            int days_cols = days_sheet.getWritableSheet().getColumns();
            if (days_cols != 6) {
                Toast.makeText(getContext(), "days 数据不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            if (days_rows > 1) {
                for (int i = 0; i < days_rows-1; i++) {
                    Days day = new Days();
                    day.setID(Long.parseLong(days_sheet.getCellContent(0, i+1)));
                    day.setPlanId(Integer.parseInt(days_sheet.getCellContent(1, i+1)));
                    day.setData(days_sheet.getCellContent(2, i+1));
                    day.setCreateTime(DateUtil.getDateFromString(days_sheet.getCellContent(3, i+1)));
                    day.setUpdateTime(DateUtil.getDateFromString(days_sheet.getCellContent(4, i+1)));
                    day.setStatus(days_sheet.getCellContent(5, i+1));
                    days_list.add(day);
                    //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            }
            days_sheet.close();
            //Event
            events_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(get_file_path))
                    .openSheet(2);//打开第3个sheet Events
            int events_rows = events_sheet.getWritableSheet().getRows();
            int events_cols = events_sheet.getWritableSheet().getColumns();
            if (events_cols != 6) {
                Toast.makeText(getContext(), "events 数据不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            if (events_rows > 1) {
                for (int i = 0; i < events_rows-1; i++) {
                    Events event = new Events();
                    event.setID(Long.parseLong(events_sheet.getCellContent(0, i+1)));
                    event.setType(events_sheet.getCellContent(1, i+1));
                    event.setCreateTime(DateUtil.getDateFromString(events_sheet.getCellContent(2, i+1)));
                    event.setData(events_sheet.getCellContent(3, i+1));
                    event.setUpdateTime(DateUtil.getDateFromString(events_sheet.getCellContent(4, i+1)));
                    event.setStatus(events_sheet.getCellContent(5, i+1));
                    events_list.add(event);
//                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            }
            events_sheet.close();
            //Plan
            plans_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(get_file_path))
                    .openSheet(3);//打开第4个sheet Plans
            int plans_rows = plans_sheet.getWritableSheet().getRows();
            int plans_cols = plans_sheet.getWritableSheet().getColumns();
            if (plans_cols != 8) {
                Toast.makeText(getContext(), "plans 数据不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            if (plans_rows > 1) {
                for (int i = 0; i < plans_rows-1; i++) {
                    Plans plan = new Plans();
                    plan.setID(Long.parseLong(plans_sheet.getCellContent(0, i+1)));
                    plan.setProjectId(Integer.parseInt(plans_sheet.getCellContent(1, i+1)));
                    plan.setData(plans_sheet.getCellContent(2, i+1));
                    plan.setCreateTime(DateUtil.getDateFromString(plans_sheet.getCellContent(3, i+1)));
                    plan.setUpdateTime(DateUtil.getDateFromString(plans_sheet.getCellContent(4, i+1)));
                    plan.setEndTime(DateUtil.getDateFromString(plans_sheet.getCellContent(5, i+1)));
                    plan.setStatus(plans_sheet.getCellContent(6, i+1));
                    plan.setWeekNum(plans_sheet.getCellContent(7, i+1));
                    plans_list.add(plan);
//                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            }
            plans_sheet.close();
            projects_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(get_file_path))
                    .openSheet(4);//打开第5个sheet Projects
            int projects_rows = projects_sheet.getWritableSheet().getRows();
            int projects_cols = projects_sheet.getWritableSheet().getColumns();
            if (projects_cols != 4) {
                Toast.makeText(getContext(), "projects 数据不合法", Toast.LENGTH_SHORT).show();
                return;
            }

            if (projects_rows > 1) {
                for (int i = 0; i < projects_rows-1; i++) {
                    Projects project = new Projects();
                    project.setID(Long.parseLong(projects_sheet.getCellContent(0, i+1)));
                    project.setName(projects_sheet.getCellContent(1, i+1));
                    project.setCreateTime(DateUtil.getDateFromString(projects_sheet.getCellContent(2, i+1)));
                    project.setUpdateTime(DateUtil.getDateFromString(projects_sheet.getCellContent(3, i+1)));
                    projects_list.add(project);
//                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            }
            projects_sheet.close();

            DaoMaster.dropAllTables(new DaoMaster.DevOpenHelper(getContext(), "week_plan.db").getWritableDb(), true);
            DaoMaster.createAllTables(new DaoMaster.DevOpenHelper(getContext(), "week_plan.db").getWritableDb(), true);
            if(collect_list.size()>0){
                ExCollectDao.getInstance().InsertOrReplaceCollects(getContext(), collect_list);
            }
            if(days_list.size()>0){
                ExDaysDao.getInstance().InsertOrReplaceDays(getContext(), days_list);
            }
            if(events_list.size()>0){
                ExEventsDao.getInstance().InsertOrReplaceEvents(getContext(), events_list);
            }
            if(plans_list.size()>0){
                ExPlansDao.getInstance().InsertOrReplacePlans(getContext(), plans_list);
            }
            if(projects_list.size()>0){
                ExProjectsDao.getInstance().InsertOrReplaceProjects(getContext(), projects_list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "同步失败", Toast.LENGTH_SHORT).show();
            try {
                if (collect_sheet != null) {
                    collect_sheet.close();
                }
                if (days_sheet != null) {
                    days_sheet.close();
                }
                if (events_sheet != null) {
                    events_sheet.close();
                }
                if (plans_sheet != null) {
                    plans_sheet.close();
                }
                if (projects_sheet != null) {
                    projects_sheet.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            alert = null;
            builder = new AlertDialog.Builder(mContext);
            alert = builder
                    .setTitle("系统提示：")
                    .setMessage("数据同步完成")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(mContext, "导出表格完成 "+PATH + file_name + ".xls", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alert.show();
        }
    }

    /**
     * 导出表格
     */
    public void ExcelOut() {
        //获取所有表数据
        List<Collect> collect_list = ExCollectDao.getInstance().GetAllCollect(getContext());
        List<Days> days_list = ExDaysDao.getInstance().GetAllDays(getContext());
        List<Events> events_list = ExEventsDao.getInstance().GetAllEvents(getContext());
        List<Plans> plans_list = ExPlansDao.getInstance().GetAllPlans(getContext());
        List<Projects> projects_list = ExProjectsDao.getInstance().GetAllProjects(getContext());

        String[] collect_srr = {"ID", "NAME", "CONTENTS", "CREATE_TIME", "UPDATE_TIME", "STATUS"};
        String[] days_srr = {"ID", "PLAN_ID", "DATA", "CREATE_TIME", "UPDATE_TIME", "STATUS"};
        String[] events_srr = {"ID", "TYPE", "CREATE_TIME", "DATA", "UPDATE_TIME", "STATUS"};
        String[] plans_srr = {"ID", "PROJECT_ID", "DATA", "CREATE_TIME", "UPDATE_TIME", "END_TIME", "STATUS", "WEEK_NUM"};
        String[] projects_srr = {"ID", "NAME", "CREATE_TIME", "UPDATE_TIME"};

        try {
            //写入表格
            ZzExcelCreator
                    .getInstance()
                    .createExcel(PATH, file_name)
                    .createSheet("Projects")
                    .close();
            ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))  //如果不想覆盖文件，注意是openExcel
                    .createSheet("Plans")
                    .close();
            ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))  //如果不想覆盖文件，注意是openExcel
                    .createSheet("Events")
                    .close();
            ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))  //如果不想覆盖文件，注意是openExcel
                    .createSheet("Days")
                    .close();
            ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))  //如果不想覆盖文件，注意是openExcel
                    .createSheet("Collect")
                    .close();

            //添加数据
            ZzExcelCreator collect_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))
                    .openSheet(0);//打开第1个sheet Collect

            //设置单元格格式
            WritableCellFormat collect_cellFormat = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(11)
                    .setFontBold(false)
                    .setUnderline(false)
                    .setItalic(false)
                    .setWrapContent(true, 100)
                    .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                    .getCellFormat();

            //写入标题 collect
            for (int i = 0; i < collect_srr.length; i++) {
                collect_sheet.fillContent(i, 0, collect_srr[i], collect_cellFormat);
            }

            if (collect_list != null && collect_list.size() > 0) {
                for (int t = 0; t < collect_srr.length; t++) {
                    for (int i = 0; i < collect_list.size(); i++) {
                        if (t == 0) {
                            collect_sheet.fillContent(t, i + 1, collect_list.get(i).getID().toString(), collect_cellFormat);
                        }
                        if (t == 1) {
                            collect_sheet.fillContent(t, i + 1, collect_list.get(i).getName(), collect_cellFormat);
                        }
                        if (t == 2) {
                            collect_sheet.fillContent(t, i + 1, collect_list.get(i).getContents(), collect_cellFormat);
                        }
                        if (t == 3) {
                            collect_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(collect_list.get(i).getCreateTime()), collect_cellFormat);
                        }
                        if (t == 4) {
                            collect_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(collect_list.get(i).getUpdateTime()), collect_cellFormat);
                        }
                        if (t == 5) {
                            collect_sheet.fillContent(t, i + 1, collect_list.get(i).getStatus(), collect_cellFormat);
                        }
                    }
                }
            }
            collect_sheet.close();

            ZzExcelCreator days_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))
                    .openSheet(1);//打开第2个sheet Days

            WritableCellFormat day_cellFormat = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(11)
                    .setFontBold(false)
                    .setUnderline(false)
                    .setItalic(false)
                    .setWrapContent(true, 100)
                    .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                    .getCellFormat();

            //写入标题 days
            for (int i = 0; i < days_srr.length; i++) {
                days_sheet.fillContent(i, 0, days_srr[i], day_cellFormat);
            }

            if (days_list != null && days_list.size() > 0) {
                for (int t = 0; t < days_srr.length; t++) {
                    for (int i = 0; i < days_list.size(); i++) {
                        if (t == 0) {
                            days_sheet.fillContent(t, i + 1, days_list.get(i).getID().toString(), day_cellFormat);
                        }
                        if (t == 1) {
                            days_sheet.fillContent(t, i + 1, days_list.get(i).getPlanId() + "", day_cellFormat);
                        }
                        if (t == 2) {
                            days_sheet.fillContent(t, i + 1, days_list.get(i).getData(), day_cellFormat);
                        }
                        if (t == 3) {
                            days_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(days_list.get(i).getCreateTime()), day_cellFormat);
                        }
                        if (t == 4) {
                            days_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(days_list.get(i).getUpdateTime()), day_cellFormat);
                        }
                        if (t == 5) {
                            days_sheet.fillContent(t, i + 1, days_list.get(i).getStatus(), day_cellFormat);
                        }
                    }
                }
            }

            days_sheet.close();


            ZzExcelCreator events_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))
                    .openSheet(2);//打开第3个sheet Events

            WritableCellFormat event_cellFormat = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(11)
                    .setFontBold(false)
                    .setUnderline(false)
                    .setItalic(false)
                    .setWrapContent(true, 100)
                    .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                    .getCellFormat();

            //写入标题 events
            for (int i = 0; i < events_srr.length; i++) {
                events_sheet.fillContent(i, 0, events_srr[i], event_cellFormat);
            }
            if (events_list != null && events_list.size() > 0) {
                for (int t = 0; t < events_srr.length; t++) {
                    for (int i = 0; i < events_list.size(); i++) {
                        if (t == 0) {
                            events_sheet.fillContent(t, i + 1, events_list.get(i).getID().toString(), event_cellFormat);
                        }
                        if (t == 1) {
                            events_sheet.fillContent(t, i + 1, events_list.get(i).getType(), event_cellFormat);
                        }
                        if (t == 2) {
                            events_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(events_list.get(i).getCreateTime()), event_cellFormat);
                        }
                        if (t == 3) {
                            events_sheet.fillContent(t, i + 1, events_list.get(i).getData(), event_cellFormat);
                        }
                        if (t == 4) {
                            events_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(events_list.get(i).getUpdateTime()), event_cellFormat);
                        }
                        if (t == 5) {
                            events_sheet.fillContent(t, i + 1, events_list.get(i).getStatus(), event_cellFormat);
                        }
                    }
                }
            }
            events_sheet.close();


            ZzExcelCreator plans_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))
                    .openSheet(3);//打开第4个sheet Plans

            WritableCellFormat plan_cellFormat = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(11)
                    .setFontBold(false)
                    .setUnderline(false)
                    .setItalic(false)
                    .setWrapContent(true, 100)
                    .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                    .getCellFormat();

            //写入标题  plans
            for (int i = 0; i < plans_srr.length; i++) {
                plans_sheet.fillContent(i, 0, plans_srr[i], plan_cellFormat);
            }
            if (plans_list != null && plans_list.size() > 0) {
                for (int t = 0; t < plans_srr.length; t++) {
                    for (int i = 0; i < plans_list.size(); i++) {
                        if (t == 0) {
                            plans_sheet.fillContent(t, i + 1, plans_list.get(i).getID().toString(), plan_cellFormat);
                        }
                        if (t == 1) {
                            plans_sheet.fillContent(t, i + 1, plans_list.get(i).getProjectId() + "", plan_cellFormat);
                        }
                        if (t == 2) {
                            plans_sheet.fillContent(t, i + 1, plans_list.get(i).getData(), plan_cellFormat);
                        }
                        if (t == 3) {
                            plans_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(plans_list.get(i).getCreateTime()), plan_cellFormat);
                        }
                        if (t == 4) {
                            plans_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(plans_list.get(i).getUpdateTime()), plan_cellFormat);
                        }
                        if (t == 5) {
                            plans_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(plans_list.get(i).getEndTime()), plan_cellFormat);
                        }
                        if (t == 6) {
                            plans_sheet.fillContent(t, i + 1, plans_list.get(i).getStatus(), plan_cellFormat);
                        }
                        if (t == 7) {
                            plans_sheet.fillContent(t, i + 1, plans_list.get(i).getWeekNum(), plan_cellFormat);
                        }
                    }
                }
            }
            plans_sheet.close();


            ZzExcelCreator projects_sheet = ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(PATH + file_name + ".xls"))
                    .openSheet(4);//打开第5个sheet Projects

            WritableCellFormat project_cellFormat = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(11)
                    .setFontBold(false)
                    .setUnderline(false)
                    .setItalic(false)
                    .setWrapContent(true, 100)
                    .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                    .getCellFormat();

            //写入标题  projects
            for (int i = 0; i < projects_srr.length; i++) {
                projects_sheet.fillContent(i, 0, projects_srr[i], project_cellFormat);
            }
            if (projects_list != null && projects_list.size() > 0) {
                for (int t = 0; t < projects_srr.length; t++) {
                    for (int i = 0; i < projects_list.size(); i++) {
                        if (t == 0) {
                            projects_sheet.fillContent(t, i + 1, projects_list.get(i).getID().toString(), project_cellFormat);
                        }
                        if (t == 1) {
                            projects_sheet.fillContent(t, i + 1, projects_list.get(i).getName(), project_cellFormat);
                        }
                        if (t == 2) {
                            projects_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(projects_list.get(i).getCreateTime()), project_cellFormat);
                        }
                        if (t == 3) {
                            projects_sheet.fillContent(t, i + 1, DateUtil.getStringFromDate(projects_list.get(i).getUpdateTime()), project_cellFormat);
                        }
                    }
                }
            }
            projects_sheet.close();

            alert = null;
            builder = new AlertDialog.Builder(mContext);
            alert = builder
                    .setTitle("系统提示：")
                    .setMessage("导出表格完成 " + PATH + file_name + ".xls")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(mContext, "导出表格完成 "+PATH + file_name + ".xls", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.getStackTrace().toString());
            Toast.makeText(getContext(), "导出表格出错", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(new Intent(getContext(), MessageActivity.class));
//            Bundle b = new Bundle();
//            b.putString("Message", e.getStackTrace().toString() + "\n" + e.getMessage());
//            in.putExtras(b);
//            startActivity(in);

        }
    }

    /**
     * 请求权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            Toast.makeText(getContext(), "requestCode:" + requestCode + "  申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i], Toast.LENGTH_SHORT).show();
            Log.i("Settings", "requestCode:" + requestCode + "  申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
        }

//        switch (requestCode) {
//            case 100:
//                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    Toast.makeText(getContext(), "读取存储权限授权成功", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 200:
//                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    Toast.makeText(getContext(), "写入存储权限授权成功", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }

//        //权限请求
//        if(grantResults[0] == 0 && grantResults[1] == 0){
//            ExcelOut();
//        }else{
//            Toast.makeText(getContext(), "授权失败", Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * 设置数字转盘
     */
    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(numberPicker, new ColorDrawable(getResources().getColor(R.color.color4)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 获取文件选择器的结果
     */
    @Override
    public void PickerResult(String Path) {
        SyncData(Path);
    }
}