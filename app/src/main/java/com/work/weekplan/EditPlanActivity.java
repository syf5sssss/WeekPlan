package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.work.entity.Plans;
import com.work.exdao.ExEventsDao;
import com.work.exdao.ExPlansDao;

import java.util.Date;

public class EditPlanActivity extends AppCompatActivity {


    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        mContext = this;

        TextView tv1 = findViewById(R.id.edit_plan_week_num);
        EditText et1 = findViewById(R.id.edit_plan_data);
        Button edit_bt = findViewById(R.id.edit_submit_plan);
        Button del_bt = findViewById(R.id.del_submit_plan);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b == null) {
            finish();
            return;
        }
        long id = b.getLong("UpdateId");
        if (id <= 0) {
            finish();
            return;
        }
        String data = b.getString("UpdateData");
        if(data == null || data.equals("")){
            finish();
            return;
        }
        String weekNum = b.getString("UpdateWeekNum");
        weekNum = weekNum.replace("\n","");
        if(weekNum == null || weekNum.equals("")){
            finish();
            return;
        }
        tv1.setText(weekNum);
        et1.setText(data);

        edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals("") || et1.getText().toString().trim().equals("")){
                    Toast.makeText(EditPlanActivity.this, "请填写计划内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Plans pl = new Plans();
                pl.ID = id;
                pl.UpdateTime = new Date();
                pl.Data = et1.getText().toString().trim();
                ExPlansDao.getInstance().UpdatePlans(EditPlanActivity.this, pl);
                finish();
            }
        });

        del_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setTitle("系统提示：")
                        .setMessage("确认删除?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
//                                Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ExPlansDao.getInstance().DeletePlansById(EditPlanActivity.this, id);
                                finish();
                                //Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                //创建AlertDialog对象
                alert.show();
            }
        });

    }
}