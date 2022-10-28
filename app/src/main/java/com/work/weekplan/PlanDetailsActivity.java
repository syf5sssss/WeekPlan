package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.work.Dto.Group;
import com.work.Dto.Item;
import com.work.entity.Days;
import com.work.exdao.ExDaysDao;
import com.work.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity {


    private ArrayList<Item> lData = null;
    private ListView ex_list;
    private PlanDetailsAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b == null) {
            finish();
            return;
        }
        long id = b.getLong("PlansId");
        if (id <= 0) {
//            finish();


//            return;
        }

        try {
            ex_list = findViewById(R.id.plan_details_list_view);
            lData = new ArrayList<Item>();

            lData.add(new Item("星期一\n", "", "", 0l));
            lData.add(new Item("星期二\n", "", "", 0l));
            lData.add(new Item("星期三\n", "", "", 0l));
            lData.add(new Item("星期四\n", "", "", 0l));
            lData.add(new Item("星期五\n", "", "", 0l));
            lData.add(new Item("星期六\n", "", "", 0l));
            lData.add(new Item("星期日\n", "", "", 0l));

            List<Days> list = ExDaysDao.getInstance().SelDaysByPlansId(PlanDetailsActivity.this, id);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(list.get(i).UpdateTime);
                    int week = cal.get(Calendar.DAY_OF_WEEK);
                    if (week == 1) {//星期日
                        lData.get(6).setiData(list.get(i).Data);
                        lData.get(6).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期日\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 2) {//星期一
                        lData.get(0).setiData(list.get(i).Data);
                        lData.get(0).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期一\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 3) {//星期二
                        lData.get(1).setiData(list.get(i).Data);
                        lData.get(1).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期二\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 4) {//星期三
                        lData.get(2).setiData(list.get(i).Data);
                        lData.get(2).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期三\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 5) {//星期四
                        lData.get(3).setiData(list.get(i).Data);
                        lData.get(3).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期四\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 6) {//星期五
                        lData.get(4).setiData(list.get(i).Data);
                        lData.get(4).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期五\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                    if (week == 7) {//星期六
                        lData.get(5).setiData(list.get(i).Data);
                        lData.get(5).setiDate(sdf.format(list.get(i).UpdateTime));
//                        lData.add(new Item("星期六\n", list.get(i).Data, sdf.format(list.get(i).UpdateTime), 0l));
                    }
                }
            }

            myAdapter = new PlanDetailsAdapter(lData, this);
            ex_list.setAdapter((PlanDetailsAdapter) myAdapter);
        } catch (Exception e) {
            Log.e("onCreate:", "" + e.getMessage());
            Log.e("onCreate:", "" + e.getStackTrace());
            e.printStackTrace();
        }

    }
}