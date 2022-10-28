package com.work.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 每日记录
 */
@Entity(nameInDb = "days")
public class Days {

    /**
     * 主键
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    public Long ID;
    /**
     * 计划ID
     */
    @NotNull
    public int PlanId;
    /**
     * 实际内容
     */
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
     * 状态
     */
    @NotNull
    public String Status;
    @Generated(hash = 171306073)
    public Days(Long ID, int PlanId, String Data, @NotNull Date CreateTime,
            @NotNull Date UpdateTime, @NotNull String Status) {
        this.ID = ID;
        this.PlanId = PlanId;
        this.Data = Data;
        this.CreateTime = CreateTime;
        this.UpdateTime = UpdateTime;
        this.Status = Status;
    }
    @Generated(hash = 1945121400)
    public Days() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getPlanId() {
        return this.PlanId;
    }
    public void setPlanId(int PlanId) {
        this.PlanId = PlanId;
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
    public String getStatus() {
        return this.Status;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }


}
