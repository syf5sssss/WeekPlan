package com.work.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 名言名句收藏
 */
@Entity(nameInDb = "collect")
public class Collect {

    /**
     * 主键
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    public Long ID;
    /**
     * 名人
     */
    @NotNull
    public String Name;
    /**
     * 名言
     */
    @NotNull
    public String Contents;
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

    @Generated(hash = 1817396627)
    public Collect(Long ID, @NotNull String Name, @NotNull String Contents,
            @NotNull Date CreateTime, @NotNull Date UpdateTime,
            @NotNull String Status) {
        this.ID = ID;
        this.Name = Name;
        this.Contents = Contents;
        this.CreateTime = CreateTime;
        this.UpdateTime = UpdateTime;
        this.Status = Status;
    }
    @Generated(hash = 1726975718)
    public Collect() {
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
    public String getContents() {
        return this.Contents;
    }
    public void setContents(String Contents) {
        this.Contents = Contents;
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

    @Override
    public String toString() {
        return "Collect{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Contents='" + Contents + '\'' +
                ", CreateTime=" + CreateTime +
                ", UpdateTime=" + UpdateTime +
                ", Status='" + Status + '\'' +
                '}';
    }
}
