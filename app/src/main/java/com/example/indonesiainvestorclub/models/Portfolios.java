package com.example.indonesiainvestorclub.models;

public class Portfolios {
    private String date;
    private String invest_usd;
    private String profit_usd;
    private String invest_idr;
    private String profit_idr;
    private String usdidr;

    int index;

    public Portfolios(String date, String invest_usd, String profit_usd, String invest_idr, String profit_idr, String usdidr) {
        this.date = date;
        this.invest_usd = invest_usd;
        this.profit_usd = profit_usd;
        this.invest_idr = invest_idr;
        this.profit_idr = profit_idr;
        this.usdidr = usdidr;
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
        this.date = invest_idr;
    }

    public String getUSDIDR() {
        return usdidr;
    }

    public void setUSDIDR(String usdidr) {
        this.usdidr = usdidr;
    }

    public String getProfit_usd() {
        return profit_usd;
    }

    public void setProfit_usd(String profit_usd) {
        this.profit_usd = profit_usd;
    }

    public String getProfit_idr() {
        return profit_idr;
    }

    public void setProfit_idr(String profit_idr) {
        this.profit_idr = profit_idr;
    }
}
