package com.project.indonesiainvestorclub.models;

public class UserDetailInvest {
    private CurrentData userInvestCurrent;

    public UserDetailInvest(CurrentData userInvestCurrent) {
        this.userInvestCurrent = userInvestCurrent;
    }

    public CurrentData getParticipantInvestCurrent() {
        return userInvestCurrent;
    }

    public void setParticipantInvestCurrent(CurrentData userInvestCurrent) {
        this.userInvestCurrent = userInvestCurrent;
    }
}
