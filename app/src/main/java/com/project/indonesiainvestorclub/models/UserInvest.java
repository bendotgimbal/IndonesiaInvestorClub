package com.project.indonesiainvestorclub.models;

public class UserInvest {
    private String date;
    private String userid;
    private String name;
    private String invest;
    private String statusid;
    private String status;
    private String wdid;

    int index;

    public UserInvest(String date, String userid, String name, String invest, String statusid, String status,
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

    public String getUserID() {
        return userid;
    }

    public void setUserID(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvest() {
        return invest;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWdID() {
        return wdid;
    }

    public void setWdID(String wdid) {
        this.wdid = wdid;
    }
}
