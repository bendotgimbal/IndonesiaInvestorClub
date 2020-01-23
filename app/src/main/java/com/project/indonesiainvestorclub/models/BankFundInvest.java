package com.project.indonesiainvestorclub.models;

public class BankFundInvest {
    private String name;
    private String accname;
    private String accno;

    public BankFundInvest(String name, String accname, String accno) {
        this.name = name;
        this.accname = accname;
        this.accno = accno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccName() {
        return accname;
    }

    public void setAccName(String accname) {
        this.accname = accname;
    }

    public String getAccNo() {
        return accno;
    }

    public void setAccNo(String accno) {
        this.accno = accno;
    }
}
