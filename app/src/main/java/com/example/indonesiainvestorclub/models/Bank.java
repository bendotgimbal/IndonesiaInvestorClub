package com.example.indonesiainvestorclub.models;

public class Bank {
    private String name;
    private String accname;
    private String accno;
    private String branch;
    private String address;
    private String swiftcode;
    private String status;

    public Bank(String name, String accname, String accno, String branch, String address,
        String swiftcode, String status) {
        this.name = name;
        this.accname = accname;
        this.accno = accno;
        this.branch = branch;
        this.address = address;
        this.swiftcode = swiftcode;
        this.status = status;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSwiftCode() {
        return swiftcode;
    }

    public void setSwiftCode(String swiftcode) {
        this.swiftcode = swiftcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
