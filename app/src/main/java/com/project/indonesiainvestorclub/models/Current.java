package com.project.indonesiainvestorclub.models;

import java.util.List;

public class Current {
    private List<CurrentData> currentdatas;

    public Current(List<CurrentData> currentData) {
        this.currentdatas = currentdatas;
    }

    public List<CurrentData> getCurrentData() {
        return currentdatas;
    }

    public void setCurrentData(List<CurrentData> currentdatas) {
        this.currentdatas = currentdatas;
    }
}
