package com.project.indonesiainvestorclub.models;

public class CurrentData {
    private String date;
    private String userid;
    private String name;
    private String invest;
    private String statusid;
    private String status;
    private String wdid;

    int index;

    public CurrentData(String date, String userid, String name, String invest, String statusid, String status,
                       String wdid) {
        this.date = date;
        this.userid = userid;
        this.name = name;
        this.invest = invest;
        this.statusid = statusid;
        this.status = status;
        this.wdid = wdid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateText(){
        return "Date : " +getDate();
    }

    public String getUserID() {
        return userid;
    }

    public void setUserID(String userid) {
        this.userid = userid;
    }

    public String getUserIDText(){
        return "User ID : " +getUserID();
    }

    public String getName() {
        return name;
    }

    public String getNameText(){
        return "Name : " + getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvest() {
        return invest;
    }

//    public String getInvestText(){
//        return "Invest : "+getInvest();
//    }

    public String getInvestText(){
        return "Total invest : " +getInvest();
    }

    public void setInvest(String invest) {
        this.invest = invest;
    }

    public String getStatusID() {
        return statusid;
    }

    public void setStatusID(String statusid) {
        this.statusid = statusid;
    }

    public String getUserIDValueText(){
        return "User ID Status : " +getStatusID();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText(){
        return "Status Payment : " + getStatus();
    }

    public String getWdID() {
        return wdid;
    }

    public void setWdID(String wdid) {
        this.wdid = wdid;
    }

    public String getWdIDText(){
        return "WD ID : " + getWdID();
    }
}
