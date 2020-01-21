package com.project.indonesiainvestorclub.models;

import java.util.List;

public class ParticipantInvestCurrent {
    private List<CurrentData> currents;

    public ParticipantInvestCurrent(List<CurrentData> currents) {
        this.currents = currents;
    }

    public List<CurrentData> getCurrent() {
        return currents;
    }

    public void setCurrent(List<CurrentData> currents) {
        this.currents = currents;
    }
}
