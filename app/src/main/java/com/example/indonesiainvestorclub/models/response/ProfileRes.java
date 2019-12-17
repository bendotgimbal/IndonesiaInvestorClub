package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Bank;
import com.example.indonesiainvestorclub.models.Login;
import com.example.indonesiainvestorclub.models.Profile;

import java.util.List;

public class ProfileRes {
    private List<Login> login;
    private List<Profile> profile;
    private List<Bank> bank;

    public List<Login> getLogin() {
        return login;
    }
    public void setLogin(List<Login> login) {
        this.login = login;
    }

    public List<Profile> getProfile() {
        return profile;
    }
    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    public List<Bank> getBank() {
        return bank;
    }
    public void setBank(List<Bank> bank) {
        this.bank = bank;
    }
}
