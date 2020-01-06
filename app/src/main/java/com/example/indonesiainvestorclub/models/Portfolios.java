package com.example.indonesiainvestorclub.models;

public class Portfolios {
    private String date;
    private String perlots_usd;
    private String invest_usd;
    private String profit_usd;
    private String commission_usd;
    private String invest_idr;
    private String profit_idr;
    private String commission_idr;
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

    public Portfolios(String date, String perlots_usd, String invest_usd, String commission_usd, String invest_idr, String commission_idr, String usdidr) {
        this.date = date;
        this.perlots_usd = perlots_usd;
        this.invest_usd = invest_usd;
        this.commission_usd = commission_usd;
        this.invest_idr = invest_idr;
        this.commission_idr = commission_idr;
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

    public String getPerlotsUSD() {
        return perlots_usd;
    }

    public void setPerlotsUSD(String perlots_usd) {
        this.perlots_usd = perlots_usd;
    }

    public String getInvestUSD() {
        return invest_usd;
    }

    public void setInvestUSD(String invest_usd) {
        this.invest_usd = invest_usd;
    }

    public String getCommissionUSD() {
        return commission_usd;
    }

    public void setCommissionUSD(String commission_usd) {
        this.commission_usd = commission_usd;
    }

    public String getInvestIDR() {
        return invest_idr;
    }

    public void setInvestIDR(String invest_idr) {
        this.date = invest_idr;
    }

    public String getCommissionIDR() {
        return commission_idr;
    }

    public void setCommissionID(String commission_idr) {
        this.date = commission_idr;
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
