package com.work.weekplan.slide;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.work.weekplan.R;

/**
 * ================================================
 *
 * ================================================
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView text_title;
    public TextView text_content;
    public ImageView image_view;

    public MyViewHolder(View itemView) {
        super(itemView);
//        textView = (TextView) itemView.findViewById(R.id.id_tv);
        text_title = (TextView) itemView.findViewById(R.id.tv_title);
        text_content = (TextView) itemView.findViewById(R.id.tv_content);
        image_view = (ImageView) itemView.findViewById(R.id.img_icon);
    }



}

