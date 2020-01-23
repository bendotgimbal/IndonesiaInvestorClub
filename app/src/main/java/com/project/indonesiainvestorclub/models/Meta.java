package com.project.indonesiainvestorclub.models;


public class Meta {
    private String accno;
    private String investorpass;
    private String server;

    public Meta(String accno, String investorpass, String server) {
        this.accno = accno;
        this.investorpass = investorpass;
        this.server = server;
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
