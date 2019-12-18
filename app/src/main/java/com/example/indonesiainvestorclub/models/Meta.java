package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Meta {
    private String accno;
    private String investorpass;
    private String server;
    private List<Metalist> metalist;

//    public Meta(List<Metalist> metalist, String accno, String investorpass, String server) {
//        this.metalist = metalist;
//        this.accno = accno;
//        this.investorpass = investorpass;
//        this.server = server;
//    }

    public Meta(List<Metalist> metalist) {
        this.metalist = metalist;
    }

    public List<Metalist> getMetalist() {
        return metalist;
    }

    public void setMetalist(List<Metalist> metalist) {
        this.metalist = metalist;
    }

    public String getAccNo() {
        return accno;
    }

    public void setAccNo(String accno) {
        this.accno = accno;
    }

    public String getInvestorPass() {
        return investorpass;
    }

    public void setInvestorPass(String investorpass) {
        this.investorpass = investorpass;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}