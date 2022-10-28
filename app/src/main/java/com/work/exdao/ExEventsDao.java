package com.work.exdao;

import android.content.Context;

import com.work.Dto.Params;
import com.work.dao.EventsDao;
import com.work.entity.Events;
import com.work.util.DbController;

import java.util.List;

public class ExEventsDao {

    private static ExEventsDao exEventsDao;
    /**
     * 获取单例
     */
    public static ExEventsDao getInstance(){
        if(exEventsDao == null){
            synchronized (ExEventsDao.class){
                if(exEventsDao == null){
                    exEventsDao = new ExEventsDao();
                }
            }
        }
        return exEventsDao;
    }

    /**
     * 查询事件表所有数据
     */
    public List<Events> GetAllEvents(Context context)
    {
        return DbController.getInstance(context).getEventsDao().queryBuilder().list();
    }

    /**
     * 查询事件表 状态是Enable 顺序为修改时间排序
     */
    public List<Events> SelEventsEnableOrderByUpdate(Context context)
    {
        return (List<Events>) DbController.getInstance(context).getEventsDao().queryBuilder().where(EventsDao.Properties.Status.eq(Params.Enable)).orderAsc(EventsDao.Properties.Type).orderDesc(EventsDao.Properties.UpdateTime).build().list();
    }

    /**
     * 根据id查找事件记录
     */
    public Events SelEventsById(Context context, long id){
        return DbController.getInstance(context).getEventsDao().load(id);
    }

    /**
     * 根据id删除事件记录
     */
    public void DeleteEventsById(Context context, long id)
    {
        DbController.getInstance(context).getEventsDao().deleteByKey(id);
    }
    /**
     * 修改事件记录
     */
    public void UpdateEvents(Context context, Events dto)
    {
        Events mOldDto = DbController.getInstance(context).getEventsDao().queryBuilder().where(EventsDao.Properties.ID.eq(dto.getID())).build().unique();//拿到之前的记录
        if(mOldDto !=null){
            if(dto.Status!=null && !dto.Status.equals(""))
            {
                mOldDto.Status = dto.Status;
            }
            if(dto.Data!=null && !dto.Data.equals(""))
            {
                mOldDto.Data = dto.Data;
            }
            if(dto.Type!=null && !dto.Type.equals(""))
            {
                mOldDto.Type = dto.Type;
            }
            if(dto.UpdateTime!=null)
            {
                mOldDto.UpdateTime = dto.UpdateTime;
            }
            DbController.getInstance(context).getEventsDao().update(mOldDto);
        }
    }
    /**
     * 添加事件记录
     */
    public long InsertEvents(Context context, Events events)
    {
        return DbController.getInstance(context).getEventsDao().insert(events);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public long InsertOrReplaceEvents(Context context, Events events)
    {
        return DbController.getInstance(context).getEventsDao().insertOrReplace(events);
    }

    /**
     * 批量添加数据Event
     */
    public void InsertOrReplaceEvents(Context context, List<Events> events)
    {
        DbController.getInstance(context).getEventsDao().insertOrReplaceInTx(events);
    }

}
