package com.work.weekplan.slide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.work.entity.Projects;
import com.work.exdao.ExPlansDao;
import com.work.exdao.ExProjectsDao;
import com.work.weekplan.EditPlanActivity;
import com.work.weekplan.R;

import java.util.Date;

public class EditProjectActivity extends AppCompatActivity {

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        mContext = this;

        EditText et1 = findViewById(R.id.etName);
        Button bt1 = findViewById(R.id.edit_submit_project);
        Button bt2 = findViewById(R.id.del_submit_project);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b == null) {
            finish();
            return;
        }
        long id = b.getLong("ProjectId");
        if (id <= 0) {
            finish();
            return;
        }
        Projects pro = ExProjectsDao.getInstance().SelProjectsById(EditProjectActivity.this, id);
        et1.setText(pro.Name);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改
                if(et1.getText().toString().equals("") || et1.getText().toString().trim().equals("")){
                    Toast.makeText(EditProjectActivity.this, "请填写项目名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                pro.setName(et1.getText().toString().trim());
                pro.UpdateTime = new Date();
                ExProjectsDao.getInstance().UpdateProjects(EditProjectActivity.this, pro);
                finish();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
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
                                ExProjectsDao.getInstance().DeleteProjectsById(EditProjectActivity.this, id);
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