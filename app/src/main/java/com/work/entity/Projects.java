package com.work.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目
 */
@Entity(nameInDb = "projects")
public class Projects {
    /**
     * 项目ID
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    public Long ID;
    /**
     * 项目名称
     */
    @NotNull
    public String Name;
    /**
     * 创建时间
     */
    @NotNull
    public Date CreateTime;
    /**
     * 更新时间
     */
    @NotNull
    public Date UpdateTime;


    /**
     * 总次数 （扩展变量）
     */
    @Transient
    public String TotalTimes;


    @Generated(hash = 1565798502)
    public Projects(Long ID, @NotNull String Name, @NotNull Date CreateTime,
            @NotNull Date UpdateTime) {
        this.ID = ID;
        this.Name = Name;
        this.CreateTime = CreateTime;
        this.UpdateTime = UpdateTime;
    }


    @Generated(hash = 1005158188)
    public Projects() {
    }


    public Long getID() {
        return this.ID;
    }


    public void setID(Long ID) {
        this.ID = ID;
    }


    public String getName() {
        return this.Name;
    }


    public void setName(String Name) {
        this.Name = Name;
    }


    public Date getCreateTime() {
        return this.CreateTime;
    }


    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }


    public Date getUpdateTime() {
        return this.UpdateTime;
    }


    public void setUpdateTime(Date UpdateTime) {
        this.UpdateTime = UpdateTime;
    }


    
}
