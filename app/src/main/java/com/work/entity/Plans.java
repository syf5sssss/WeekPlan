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
 * 计划
 */
@Entity(nameInDb = "plans")
public class Plans {

    /**
     * 主键
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    public Long ID;
    /**
     * 项目ID
     */
    @NotNull
    public int ProjectId;
    /**
     * 计划内容
     */
    @NotNull
    public String Data;
    /**
     * 创建时间
     */
    @NotNull
    public Date CreateTime;
    /**
     * 修改时间
     */
    @NotNull
    public Date UpdateTime;
    /**
     * 结束时间
     */
    @NotNull
    public Date EndTime;
    /**
     * 状态
     */
    @NotNull
    public String Status;

    /**
     * 周进度
     */
    @NotNull
    public String WeekNum;

    @Generated(hash = 188279960)
    public Plans(Long ID, int ProjectId, @NotNull String Data,
            @NotNull Date CreateTime, @NotNull Date UpdateTime,
            @NotNull Date EndTime, @NotNull String Status,
            @NotNull String WeekNum) {
        this.ID = ID;
        this.ProjectId = ProjectId;
        this.Data = Data;
        this.CreateTime = CreateTime;
        this.UpdateTime = UpdateTime;
        this.EndTime = EndTime;
        this.Status = Status;
        this.WeekNum = WeekNum;
    }

    @Generated(hash = 869980052)
    public Plans() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getProjectId() {
        return this.ProjectId;
    }

    public void setProjectId(int ProjectId) {
        this.ProjectId = ProjectId;
    }

    public String getData() {
        return this.Data;
    }

    public void setData(String Data) {
        this.Data = Data;
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

    public Date getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(Date EndTime) {
        this.EndTime = EndTime;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getWeekNum() {
        return this.WeekNum;
    }

    public void setWeekNum(String WeekNum) {
        this.WeekNum = WeekNum;
    }

    

    
}
