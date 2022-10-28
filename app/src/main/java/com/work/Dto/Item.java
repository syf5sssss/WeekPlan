package com.work.Dto;

public class Item {

    private String iPlan;
    private String iData;
    private String iDate;
    public Long id;

    public Item() {
    }

    public Item(String iPlan, String iData, String iDate, Long id) {
        this.iPlan = iPlan;
        this.iData = iData;
        this.iDate = iDate;
        this.id = id;
    }

    public String getiPlan() {
        return iPlan;
    }

    public String getiData() {
        return iData;
    }

    public void setiPlan(String iPlan) {
        this.iPlan = iPlan;
    }

    public void setiData(String iData) {
        this.iData = iData;
    }

    public String getiDate() {
        return iDate;
    }

    public void setiDate(String iDate) {
        this.iDate = iDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

