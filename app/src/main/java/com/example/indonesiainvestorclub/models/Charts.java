package com.example.indonesiainvestorclub.models;

public class Charts {
    private String name;
    private String invested;
    private String profit;
    private String roi;

    public Charts(String name, String invested, String profit, String roi) {
        this.name = name;
        this.invested = invested;
        this.profit = profit;
        this.roi = roi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvested() {
        return invested;
    }

    public void setInvested(String invested) {
        this.invested = invested;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }
}
