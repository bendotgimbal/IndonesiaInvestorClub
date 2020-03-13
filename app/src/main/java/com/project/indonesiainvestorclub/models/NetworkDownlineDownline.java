package com.project.indonesiainvestorclub.models;

public class NetworkDownlineDownline {
    private String group;
    private String commission_usd;
    private String commission_idr;

    public NetworkDownlineDownline() {
    }

    public NetworkDownlineDownline(String group) {
        this.group = group;
    }

    public NetworkDownlineDownline(String group, String commission_usd, String commission_idr) {
        this.group = group;
        this.commission_usd = commission_usd;
        this.commission_idr = commission_idr;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCommissionUSD() {
        return commission_usd;
    }

    public void setCommissionUSD(String commission_usd) {
        this.commission_usd = commission_usd;
    }

    public String getCommissionIDR() {
        return commission_idr;
    }

    public void setCommissionID(String commission_idr) {
        this.commission_idr = commission_idr;
    }
}
