package com.example.indonesiainvestorclub.models;

public class Transactions {
    private String name;
    private String invest_date;
    private String invest_usd;
    private String invest_idr;
    private String dp_date;
    private String dp_amount;
    private String dp_id;
    private String dp_status;
    private String dp_proof;
    private String start_date;
    private String end_date;
    private String status;
    private String wd_date;
    private String wd_amount;
    private String wd_id;
    private String wd_status;

    int index;

    public Transactions(String name, String invest_date, String invest_usd, String invest_idr, String dp_date, String dp_amount,
                        String dp_id, String dp_status, String dp_proof, String start_date, String end_date, String status,
                        String wd_date, String wd_amount, String wd_id, String wd_status) {
        this.name = name;
        this.invest_date = invest_date;
        this.invest_usd = invest_usd;
        this.invest_idr = invest_idr;
        this.dp_date = dp_date;
        this.dp_amount = dp_amount;
        this.dp_id = dp_id;
        this.dp_status = dp_status;
        this.dp_proof = dp_proof;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.wd_date = wd_date;
        this.wd_amount = wd_amount;
        this.wd_id = wd_id;
        this.wd_status = wd_status;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvestDate() {
        return invest_date;
    }

    public void setInvestDate(String invest_date) {
        this.invest_date = invest_date;
    }

    public String getInvestUSD() {
        return invest_usd;
    }

    public void setInvestUSD(String invest_usd) {
        this.invest_usd = invest_usd;
    }

    public String getInvestIDR() {
        return invest_idr;
    }

    public void setInvestIDR(String invest_idr) {
        this.invest_idr = invest_idr;
    }

    public String getDPDate() {
        return dp_date;
    }

    public void setDPDate(String dp_date) {
        this.dp_date = dp_date;
    }

    public String getDPAmount() {
        return dp_amount;
    }

    public void setDPAmount(String dp_amount) {
        this.dp_amount = dp_amount;
    }

    public String getDPID() {
        return dp_id;
    }

    public void setDPID(String dp_id) {
        this.dp_id = dp_id;
    }

    public String getDPStatus() {
        return dp_status;
    }

    public void setDPStatus(String dp_status) {
        this.dp_status = dp_status;
    }

    public String getDPProof() {
        return dp_proof;
    }

    public void setDPProof(String dp_proof) {
        this.dp_proof = dp_proof;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWDDate() {
        return wd_date;
    }

    public void setWDDate(String wd_date) {
        this.wd_date = wd_date;
    }

    public String getWDAmount() {
        return wd_amount;
    }

    public void setWDAmount(String wd_amount) {
        this.wd_amount = wd_amount;
    }

    public String getWDID() {
        return wd_id;
    }

    public void setWDID(String wd_id) {
        this.wd_id = wd_id;
    }

    public String getWDStatus() {
        return wd_status;
    }

    public void setWDStatus(String wd_status) {
        this.wd_status = wd_status;
    }
}
