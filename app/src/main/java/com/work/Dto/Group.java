package com.work.Dto;

public class Group {

    public Long ID;
    private String gTitle;
    private String gLastUpdate;
    private String gNumber;

    public Group() {
    }

    /**
     * 构造方法
     * @param gTitle 标题
     * @param gLastUpdate 上一次更新时间
     * @param gNumber 总计划数
     */
    public Group(String gTitle,String gLastUpdate, String gNumber, Long id) {
        this.ID = id;
        this.gTitle = gTitle;
        this.gLastUpdate = gLastUpdate;
        this.gNumber = gNumber;
    }

    public String getgTitle() {
        return gTitle;
    }

    public void setgTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public String getgLastUpdate() {
        return gLastUpdate;
    }

    public void setgLastUpdate(String gLastUpdate) {
        this.gLastUpdate = gLastUpdate;
    }

    public String getgNumber() {
        return gNumber;
    }

    public void setgNumber(String gNumber) {
        this.gNumber = gNumber;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}

