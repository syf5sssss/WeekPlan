package com.work.exdao;

import android.content.Context;

import com.work.Dto.Params;
import com.work.dao.PlansDao;
import com.work.entity.Plans;
import com.work.util.DbController;

import java.util.List;

public class ExPlansDao {

    private static ExPlansDao exPlansDao;
    /**
     * 获取单例
     */
    public static ExPlansDao getInstance(){
        if(exPlansDao == null){
            synchronized (ExPlansDao.class){
                if(exPlansDao == null){
                    exPlansDao = new ExPlansDao();
                }
            }
        }
        return exPlansDao;
    }

    /**
     * 查询计划表所有数据
     */
    public List<Plans> GetAllPlans(Context context)
    {
        return DbController.getInstance(context).getPlansDao().queryBuilder().list();
    }

    /**
     * 查询计划表 状态是Enable 顺序为修改时间排序
     */
    public List<Plans> SelPlansEnableOrderByUpdate(Context context)
    {
        return (List<Plans>) DbController.getInstance(context).getPlansDao().queryBuilder().where(PlansDao.Properties.Status.eq(Params.Enable)).orderDesc(PlansDao.Properties.UpdateTime).build().list();
    }

    /**
     * 根据id查找计划记录
     */
    public Plans SelPlansById(Context context, long id){
        return DbController.getInstance(context).getPlansDao().load(id);
    }
    /**
     * 根据项目id查找计划记录
     */
    public List<Plans> SelPlansByProjectId(Context context, long id){
        return DbController.getInstance(context).getPlansDao().queryBuilder().where(PlansDao.Properties.ProjectId.eq(id)).build().list();
    }

    /**
     * 根据周数量查询计划信息
     */
    public List<Plans> SelPlansByWeekNum(Context context, String num){
        return DbController.getInstance(context).getPlansDao().queryBuilder().where(PlansDao.Properties.WeekNum.eq(num)).build().list();
    }

    /**
     * 根据id删除计划记录
     */
    public void DeletePlansById(Context context, long id)
    {
        DbController.getInstance(context).getPlansDao().deleteByKey(id);
    }
    /**
     * 修改计划记录
     */
    public void UpdatePlans(Context context, Plans dto)
    {
        Plans mOldDto = DbController.getInstance(context).getPlansDao().queryBuilder().where(PlansDao.Properties.ID.eq(dto.getID())).build().unique();//拿到之前的记录
        if(mOldDto !=null){
            if(dto.Status!=null && !dto.Status.equals(""))
            {
                mOldDto.Status = dto.Status;
            }
            if(dto.Data!=null && !dto.Data.equals(""))
            {
                mOldDto.Data = dto.Data;
            }
            if(dto.UpdateTime!=null)
            {
                mOldDto.UpdateTime = dto.UpdateTime;
            }
            if(dto.EndTime!=null)
            {
                mOldDto.EndTime = dto.EndTime;
            }
            if(dto.ProjectId > 0)
            {
                mOldDto.ProjectId = dto.ProjectId;
            }
            if(dto.WeekNum!=null)
            {
                mOldDto.WeekNum = dto.WeekNum;
            }

            DbController.getInstance(context).getPlansDao().update(mOldDto);
        }
    }
    /**
     * 添加计划记录
     */
    public long InsertPlans(Context context, Plans plans)
    {
        return DbController.getInstance(context).getPlansDao().insert(plans);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public long InsertOrReplacePlans(Context context, Plans plans)
    {
        return DbController.getInstance(context).getPlansDao().insertOrReplace(plans);
    }
    /**
     * 批量添加数据Plan
     */
    public void InsertOrReplacePlans(Context context, List<Plans> plans)
    {
        DbController.getInstance(context).getPlansDao().insertOrReplaceInTx(plans);
    }

}
