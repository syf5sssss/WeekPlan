package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.work.Dto.Params;
import com.work.entity.Collect;
import com.work.entity.Events;
import com.work.exdao.ExCollectDao;
import com.work.exdao.ExEventsDao;

import java.util.Date;

public class EditEventActivity extends AppCompatActivity {


    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        mContext = this;

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

        Events ev =  ExEventsDao.getInstance().SelEventsById(EditEventActivity.this, id);
        EditText ed1 = findViewById(R.id.edit_plan_data);
        ed1.setText(ev.Data);
        RadioButton rb = findViewById(R.id.edit_radio_Enable);
        rb.setChecked(ev.Type.equals(Params.Enable));
        //修改
        findViewById(R.id.edit_submit_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("") || ed1.getText().toString().trim().equals("")){
                    Toast.makeText(EditEventActivity.this, "请填写内容", Toast.LENGTH_LONG).show();
                    return;
                }
                Events ev = new Events();
                ev.ID = id;
                if(rb.isChecked()){
                    ev.Type = Params.Enable;
                }else{
                    ev.Type = Params.Disable;
                }
                ev.Data = ed1.getText().toString().trim();
                ev.UpdateTime = new Date();
                ExEventsDao.getInstance().UpdateEvents(EditEventActivity.this, ev);
                finish();
            }
        });

        //删除
        findViewById(R.id.edit_delete_event).setOnClickListener(new View.OnClickListener() {
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
                                ExEventsDao.getInstance().DeleteEventsById(EditEventActivity.this, id);
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