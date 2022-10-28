package com.work.weekplan.slide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.entity.Events;
import com.work.view.SwipeMenuLayout;
import com.work.view.SwipeMenuView;
import com.work.weekplan.EventsFragment;
import com.work.weekplan.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 自定义适配器
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Events> mData;

    private static final int ONE_ITEM = 0;
    private static final int TWP_ITEM = 1;

    private Context mContext;
    private SwipeMenuView menuView_Ok;
    private SwipeMenuView menuView_Ng;
    private SwipeMenuBuilder swipeMenuBuilder;

    public RecyclerAdapter(List<Events> mData, Context context, EventsFragment ef) {
        this.mData = mData;
        this.mContext = context;
        swipeMenuBuilder = (SwipeMenuBuilder) ef;
    }

    /**
     * 获取当前信息需要加载的模板
     */
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("Enable")) {
            return ONE_ITEM;
        } else {
            return TWP_ITEM;
        }
    }

    /**
     * 获取需要加载的模板页面
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        SwipeMenuView menuView_Ok = swipeMenuBuilder.createOK();
        SwipeMenuView menuView_Ng = swipeMenuBuilder.createNG();
        //包装用户的item布局
        if (viewType == ONE_ITEM) {
            View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listview_events_ok, viewGroup, false);
            SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView_Ok, new BounceInterpolator(), new LinearInterpolator());
            OKHolder holder = new OKHolder(swipeMenuLayout);
            setListener(viewGroup, holder, viewType);
            return holder;
        } else {
            View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listview_events_ng, viewGroup, false);
            SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView_Ng, new BounceInterpolator(), new LinearInterpolator());
            NGHolder holder = new NGHolder(swipeMenuLayout);
            setListener(viewGroup, holder, viewType);
            return holder;
        }
    }

    /**
     * 列表传值
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
            if (holder instanceof OKHolder){
                ((OKHolder) holder).content.setText("\n"+mData.get(position).getData());
                ((OKHolder) holder).title.setText(sdf.format(mData.get(position).getCreateTime()));
            }else {
                ((NGHolder) holder).content.setText("\n"+mData.get(position).getData());
                ((NGHolder) holder).title.setText(sdf.format(mData.get(position).getCreateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取展示信息总数
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 在界面上删除某条数据
     */
    public void remove(int pos) {
        this.notifyItemRemoved(pos);
    }

    /**
     * 为了添加监听事件
     */
    protected void setListener(final ViewGroup parent, final RecyclerView.ViewHolder viewHolder, int viewType) {
        //短按
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, (T) mData.get(position).Data, position);
                }
            }
        });
        //长按
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, (T) mData.get(position).Data, position);
                }
                return false;
            }
        });
    }

    public OnItemClickListener<T> mOnItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(View view, RecyclerView.ViewHolder holder, T o, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, T o, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 已完成 模板组件
     */
    static class OKHolder extends RecyclerView.ViewHolder {

        ImageView img_icon;
        TextView title;
        TextView content;

        public OKHolder(@NonNull View itemView) {
            super(itemView);
            img_icon = itemView.findViewById(R.id.img_icon);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
        }

    }

    /**
     * 未完成 模板组件
     */
    static class NGHolder extends RecyclerView.ViewHolder {

        ImageView img_icon;
        TextView title;
        TextView content;

        public NGHolder(@NonNull View itemView) {
            super(itemView);
            img_icon = itemView.findViewById(R.id.img_icon);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

}
