package com.work.weekplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b == null) {
            finish();
            return;
        }
        String str = b.getString("Message");
        if (str.equals("")||str.trim().equals("")) {
            finish();
            return;
        }

        TextView tv = findViewById(R.id.message_text);
        tv.setText(str);

    }




}