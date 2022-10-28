package com.work.exdao;

import android.content.Context;

import com.work.Dto.Params;
import com.work.dao.DaysDao;
import com.work.entity.Days;
import com.work.util.DbController;

import java.util.Date;
import java.util.List;

public class ExDaysDao {

    private static ExDaysDao exDaysDao;
    /**
     * 获取单例
     */
    public static ExDaysDao getInstance(){
        if(exDaysDao == null){
            synchronized (ExDaysDao.class){
                if(exDaysDao == null){
                    exDaysDao = new ExDaysDao();
                }
            }
        }
        return exDaysDao;
    }

    /**
     * 查询今天表所有数据
     */
    public List<Days> GetAllDays(Context context)
    {
        return DbController.getInstance(context).getDaysDao().queryBuilder().list();
    }

    /**
     * 查询今天表 状态是Enable 顺序为修改时间排序
     */
    public List<Days> SelDaysEnableOrderByUpdate(Context context)
    {
        return (List<Days>) DbController.getInstance(context).getDaysDao().queryBuilder().where(DaysDao.Properties.Status.eq(Params.Enable)).orderDesc(DaysDao.Properties.UpdateTime).build().list();
    }

    /**
     * 根据id查找今天记录
     */
    public Days SelDaysById(Context context, long id){
        return DbController.getInstance(context).getDaysDao().load(id);
    }
    /**
     * 根据计划id查找今天记录
     */
    public List<Days> SelDaysByPlansId(Context context, long id){
        return DbController.getInstance(context).getDaysDao().queryBuilder().where(DaysDao.Properties.PlanId.eq(id)).build().list();
    }


    /**
     * 根据id删除今天记录
     */
    public void DeleteDaysById(Context context, long id)
    {
        DbController.getInstance(context).getDaysDao().deleteByKey(id);
    }
    /**
     * 修改今天记录
     */
    public void UpdateDays(Context context, Days dto)
    {
        Days mOldDto = DbController.getInstance(context).getDaysDao().queryBuilder().where(DaysDao.Properties.ID.eq(dto.getID())).build().unique();//拿到之前的记录
        if(mOldDto !=null){
            if(dto.Status!=null && !dto.Status.equals(""))
            {
                mOldDto.Status = dto.Status;
            }
            if(dto.Data!=null && !dto.Data.equals(""))
            {
                mOldDto.Data = dto.Data;
            }
            if(dto.PlanId>0)
            {
                mOldDto.PlanId = dto.PlanId;
            }
            if(dto.UpdateTime!=null)
            {
                mOldDto.UpdateTime = dto.UpdateTime;
            }

            DbController.getInstance(context).getDaysDao().update(mOldDto);
        }
    }
    /**
     * 添加今天记录
     */
    public long InsertDays(Context context, Days days)
    {
        return DbController.getInstance(context).getDaysDao().insert(days);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public long InsertOrReplaceDays(Context context, Days days)
    {
        return DbController.getInstance(context).getDaysDao().insertOrReplace(days);
    }

    /**
     * 批量添加数据Day
     */
    public void InsertOrReplaceDays(Context context, List<Days> days)
    {
        DbController.getInstance(context).getDaysDao().insertOrReplaceInTx(days);
    }


}
