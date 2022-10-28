package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.work.Dto.Params;
import com.work.entity.Plans;
import com.work.exdao.ExPlansDao;
import com.work.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddPlanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        TextView tv1 = findViewById(R.id.add_plan_week_num);
//        EditText et1 = findViewById(R.id.etName);
        EditText et2 = findViewById(R.id.add_plan_data);
        Button bt = findViewById(R.id.submit_plan);

        String str = DateUtil.GetYear_Week_Num();
        tv1.setText(str);
//        et1.setText("");
        et2.setText("");

        List<Plans> list = ExPlansDao.getInstance().GetAllPlans(AddPlanActivity.this);
        if(list!=null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            for(int i=0;i<list.size();i++){
                cal.setTime(list.get(i).getCreateTime());
                if(list.get(i).getWeekNum().equals(str) && cal.get(Calendar.YEAR) == year){
                    Toast.makeText(getApplicationContext(),str+" 周已有计划",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        }

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b == null) {
            finish();
            return;
        }
        long id = b.getLong("ProjectId");
        if (id < 0) {
            finish();
            return;
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(et1.getText().toString().equals("") || et1.getText().toString().trim().equals("")){
//                    Toast.makeText(getApplicationContext(),"请填写计划名称",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(et2.getText().toString().equals("") || et2.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"请填写计划内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                Plans pl = new Plans();
                pl.Status = Params.Enable;
                pl.ProjectId = (int)id;
                pl.EndTime = DateUtil.getDateFromString(DateUtil.getWeekEnd());
                pl.Data = et2.getText().toString().trim();
                pl.CreateTime = new Date();
                pl.UpdateTime = new Date();
                pl.WeekNum = str;

                ExPlansDao.getInstance().InsertPlans(AddPlanActivity.this, pl);

                finish();
            }
        });


    }




}