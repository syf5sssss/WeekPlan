package com.work.exdao;

import android.content.Context;

import com.work.dao.ProjectsDao;
import com.work.entity.Projects;
import com.work.util.DbController;

import java.util.List;

public class ExProjectsDao {

    private static ExProjectsDao exProjectsDao;
    /**
     * 获取单例
     */
    public static ExProjectsDao getInstance(){
        if(exProjectsDao == null){
            synchronized (ExProjectsDao.class){
                if(exProjectsDao == null){
                    exProjectsDao = new ExProjectsDao();
                }
            }
        }
        return exProjectsDao;
    }

    /**
     * 查询项目表所有数据
     */
    public List<Projects> GetAllProjects(Context context)
    {
        return DbController.getInstance(context).getProjectsDao().queryBuilder().list();
    }

    /**
     * 查询项目表 状态是Enable 顺序为修改时间排序
     */
    public List<Projects> SelProjectsEnableOrderByUpdate(Context context)
    {
        return (List<Projects>) DbController.getInstance(context).getProjectsDao().queryBuilder().orderDesc(ProjectsDao.Properties.UpdateTime).build().list();
    }

    /**
     * 根据id查找项目记录
     */
    public Projects SelProjectsById(Context context, long id){
        return DbController.getInstance(context).getProjectsDao().load(id);
    }

    /**
     * 根据id删除项目记录
     */
    public void DeleteProjectsById(Context context, long id)
    {
        DbController.getInstance(context).getProjectsDao().deleteByKey(id);
    }
    /**
     * 修改项目记录
     */
    public void UpdateProjects(Context context, Projects dto)
    {
        Projects mOldDto = DbController.getInstance(context).getProjectsDao().queryBuilder().where(ProjectsDao.Properties.ID.eq(dto.getID())).build().unique();//拿到之前的记录
        if(mOldDto !=null){
            if(dto.Name!=null && !dto.Name.equals(""))
            {
                mOldDto.Name = dto.Name;
            }
            if(dto.TotalTimes!=null && !dto.TotalTimes.equals(""))
            {
                mOldDto.TotalTimes = dto.TotalTimes;
            }
            if(dto.UpdateTime!=null)
            {
                mOldDto.UpdateTime = dto.UpdateTime;
            }
            DbController.getInstance(context).getProjectsDao().update(mOldDto);
        }
    }
    /**
     * 添加项目记录
     */
    public long InsertProjects(Context context, Projects projects)
    {
        return DbController.getInstance(context).getProjectsDao().insert(projects);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public long InsertOrReplaceProjects(Context context, Projects projects)
    {
        return DbController.getInstance(context).getProjectsDao().insertOrReplace(projects);
    }
    /**
     * 会自动判断是添加还是替换
     */
    public void InsertOrReplaceProjects(Context context, List<Projects> projects)
    {
        DbController.getInstance(context).getProjectsDao().insertOrReplaceInTx(projects);
    }

}
