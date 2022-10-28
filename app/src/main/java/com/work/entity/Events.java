package com.work.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 事件
 */
@Entity(nameInDb = "events")
public class Events {

    /**
     * 主键
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    public Long ID;
    /**
     * 事件类型 已完成，未完成
     */
    @NotNull
    public String Type;
    /**
     * 创建时间
     */
    @NotNull
    public Date CreateTime;
    /**
     * 内容
     */
    @NotNull
    public String Data;
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
    @Generated(hash = 522439028)
    public Events(Long ID, @NotNull String Type, @NotNull Date CreateTime,
            @NotNull String Data, @NotNull Date UpdateTime,
            @NotNull String Status) {
        this.ID = ID;
        this.Type = Type;
        this.CreateTime = CreateTime;
        this.Data = Data;
        this.UpdateTime = UpdateTime;
        this.Status = Status;
    }
    @Generated(hash = 2113269558)
    public Events() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getType() {
        return this.Type;
    }
    public void setType(String Type) {
        this.Type = Type;
    }
    public Date getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }
    public String getData() {
        return this.Data;
    }
    public void setData(String Data) {
        this.Data = Data;
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
