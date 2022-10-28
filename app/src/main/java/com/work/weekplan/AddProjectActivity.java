package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.work.entity.Projects;
import com.work.exdao.ExProjectsDao;

import java.util.Date;

public class AddProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        EditText et = findViewById(R.id.etName);
        et.setText("");

        //按钮
        findViewById(R.id.submit_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().toString().equals("") || et.getText().toString().trim().equals("")){
                    Toast.makeText(AddProjectActivity.this,"请填写项目名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                Projects pj = new Projects();
                pj.UpdateTime = new Date();
                pj.Name = et.getText().toString().trim();
                pj.CreateTime = new Date();
                ExProjectsDao.getInstance().InsertProjects(AddProjectActivity.this, pj);
                finish();
            }
        });
    }
}