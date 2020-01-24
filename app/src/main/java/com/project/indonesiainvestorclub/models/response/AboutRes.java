package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.About;

import java.util.List;

public class AboutRes {
    private List<About> about;

    public AboutRes(List<About> about) {
        this.about = about;
    }

    public List<About> getAbout() {
        return about;
    }

    public void setAbout(List<About> about) {
        this.about = about;
    }
}
