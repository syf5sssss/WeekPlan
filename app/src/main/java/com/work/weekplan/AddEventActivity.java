package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.work.Dto.Params;
import com.work.entity.Events;
import com.work.exdao.ExCollectDao;
import com.work.exdao.ExEventsDao;

import java.util.Date;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        EditText ed1 = findViewById(R.id.add_plan_data);
        ed1.setText("");

        //按钮
        findViewById(R.id.add_submit_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = findViewById(R.id.add_radio_Enable);
                boolean check = rb.isChecked();
                //添加事件
                Events et = new Events();
                et.Data = ed1.getText().toString();
                et.Status = Params.Enable;
                et.UpdateTime = new Date();
                et.CreateTime = new Date();
                et.Type = check ? Params.Enable:Params.Disable;
                ExEventsDao.getInstance().InsertEvents(AddEventActivity.this, et);
                finish();
            }
        });

    }
}