package com.work.exdao;

import android.content.Context;

import com.work.Dto.Params;
import com.work.dao.CollectDao;
import com.work.entity.Collect;
import com.work.util.DbController;
import com.work.weekplan.MainActivity;

import java.util.List;

public class ExCollectDao {

    private static ExCollectDao exCollectDao;
    /**
     * 获取单例
     */
    public static ExCollectDao getInstance(){
        if(exCollectDao == null){
            synchronized (ExCollectDao.class){
                if(exCollectDao == null){
                    exCollectDao = new ExCollectDao();
                }
            }
        }
        return exCollectDao;
    }

    /**
     * 查询收藏表所有数据
     */
    public List<Collect> GetAllCollect(Context context)
    {
        return DbController.getInstance(context).getCollectDao().queryBuilder().list();
    }

    /**
     * 查询收藏表 状态是Enable 顺序为修改时间排序
     */
    public List<Collect> SelCollectEnableOrderByUpdate(Context context)
    {
        return (List<Collect>) DbController.getInstance(context).getCollectDao().queryBuilder().where(CollectDao.Properties.Status.eq(Params.Enable)).orderDesc(CollectDao.Properties.UpdateTime).build().list();
    }

    /**
     * 根据id查找收藏记录
     */
    public Collect SelCollectById(Context context, long id){
        return DbController.getInstance(context).getCollectDao().load(id);
    }

    /**
     * 根据id删除收藏记录
     */
    public void DeleteCollectById(Context context, long id)
    {
        DbController.getInstance(context).getCollectDao().deleteByKey(id);
    }
    /**
     * 修改收藏记录
     */
    public void UpdateCollect(Context context, Collect collect)
    {
        Collect mOldCollect = DbController.getInstance(context).getCollectDao().queryBuilder().where(CollectDao.Properties.ID.eq(collect.getID())).build().unique();//拿到之前的记录
        if(mOldCollect !=null){
            if(collect.Status!=null && !collect.Status.equals(""))
            {
                mOldCollect.Status = collect.Status;
            }
            if(collect.Contents!=null && !collect.Contents.equals(""))
            {
                mOldCollect.Contents = collect.Contents;
            }
            if(collect.Name!=null && !collect.Name.equals(""))
            {
                mOldCollect.Name = collect.Name;
            }
            if(collect.UpdateTime!=null)
            {
                mOldCollect.UpdateTime = collect.UpdateTime;
            }
            DbController.getInstance(context).getCollectDao().update(mOldCollect);
        }
    }
    /**
     * 添加收藏记录
     */
    public long InsertCollect(Context context, Collect collect)
    {
        return DbController.getInstance(context).getCollectDao().insert(collect);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public long InsertOrReplaceCollect(Context context, Collect collect)
    {
        return DbController.getInstance(context).getCollectDao().insertOrReplace(collect);
    }

    /**
     * 批量添加数据
     */
    public void InsertOrReplaceCollects(Context context, List<Collect> collects)
    {
         DbController.getInstance(context).getCollectDao().insertOrReplaceInTx(collects);
    }

}
