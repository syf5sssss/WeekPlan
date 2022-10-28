package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.work.Dto.Params;
import com.work.entity.Collect;
import com.work.exdao.ExCollectDao;
import com.work.util.DbController;

import java.util.Date;

public class AddCollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collect);

        EditText ed1 = findViewById(R.id.add_collect_author);
        ed1.setText("");
        EditText ed2 = findViewById(R.id.add_collect_data);
        ed2.setText("");

        //按钮
        findViewById(R.id.submit_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //获取作者
                    EditText et = findViewById(R.id.add_collect_author);
                    String author_str = et.getText().toString().trim();
                    if(author_str.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"请填写作者名称",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //获取名言
                    EditText et2 = findViewById(R.id.add_collect_data);
                    String collect_data = et2.getText().toString().trim();
                    if(collect_data.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"请填写作者名言",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Collect col = new Collect();
                    col.Name = author_str;
                    col.Contents = collect_data;
                    col.Status = Params.Enable;
                    col.CreateTime = new Date();
                    col.UpdateTime = new Date();
                    Log.e("","collect:"+col.toString());
                    long id = ExCollectDao.getInstance().InsertCollect(AddCollectActivity.this,col);
                    Log.e("","==========添加了一条收藏========== id:"+id);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}