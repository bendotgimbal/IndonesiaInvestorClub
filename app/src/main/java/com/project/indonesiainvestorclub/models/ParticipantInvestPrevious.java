package com.project.indonesiainvestorclub.models;

public class ParticipantInvestPrevious {
    private String date;
    private String invest;

    public ParticipantInvestPrevious(String date, String invest) {
        this.date = date;
        this.invest = invest;
    }

    public String getDate() {
        return date;
    }

    public String getDateText(){
        return "Date : " +getDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvest() {
        return invest;
    }

    public String getInvestText(){
        return "Total invest : " +getInvest();
    }

    public void setInvest(String invest) {
        this.invest = invest;
    }
}
