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
import android.widget.Toast;

import com.work.entity.Collect;
import com.work.exdao.ExCollectDao;
import com.work.util.DbController;

import java.util.Date;

public class EditCollectActivity extends AppCompatActivity {

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collect);
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

        Collect col = ExCollectDao.getInstance().SelCollectById(EditCollectActivity.this, id);
        Log.e("", "col:" + col.toString());

        EditText ed1 = findViewById(R.id.edit_collect_author);
        EditText ed2 = findViewById(R.id.edit_collect_data);
        ed1.setText(col.Name);
        ed2.setText(col.Contents);

        //修改
        findViewById(R.id.submit_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collect update_col = new Collect();
                update_col.ID = id;
                update_col.setName(ed1.getText().toString().trim());
                update_col.setContents(ed2.getText().toString().trim());
                update_col.setUpdateTime(new Date());
                ExCollectDao.getInstance().UpdateCollect(EditCollectActivity.this, update_col);
                finish();
            }
        });

        //删除
        findViewById(R.id.delete_collect).setOnClickListener(new View.OnClickListener() {
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
                                ExCollectDao.getInstance().DeleteCollectById(EditCollectActivity.this, id);
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