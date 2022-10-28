package com.work.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.work.dao.CollectDao;
import com.work.dao.DaoMaster;
import com.work.dao.DaoSession;
import com.work.dao.DaysDao;
import com.work.dao.EventsDao;
import com.work.dao.PlansDao;
import com.work.dao.ProjectsDao;
import com.work.entity.Collect;

import java.util.List;

public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * collect
     */
    private CollectDao collectDao;
    private DaysDao daysDao;
    private EventsDao eventsDao;
    private PlansDao plansDao;
    private ProjectsDao projectsDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"week_plan.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        collectDao = mDaoSession.getCollectDao();
        daysDao = mDaoSession.getDaysDao();
        eventsDao = mDaoSession.getEventsDao();
        plansDao = mDaoSession.getPlansDao();
        projectsDao = mDaoSession.getProjectsDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"week_plan.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"week_plan.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    public CollectDao getCollectDao() {
        return collectDao;
    }

    public DaysDao getDaysDao() {
        return daysDao;
    }

    public EventsDao getEventsDao() {
        return eventsDao;
    }

    public PlansDao getPlansDao() {
        return plansDao;
    }

    public ProjectsDao getProjectsDao() {
        return projectsDao;
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public Context getContext() {
        return context;
    }

    public DaoMaster.DevOpenHelper getmHelper() {
        return mHelper;
    }

    /**
     * 按条件查询数据
     */
    public List<Collect> searchByWhere(String condition){
        return (List<Collect>) collectDao.queryBuilder().where(CollectDao.Properties.Name.eq(condition)).build().unique();
    }

}
