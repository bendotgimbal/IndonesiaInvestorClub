package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Bank;
import com.example.indonesiainvestorclub.models.Documents;
import com.example.indonesiainvestorclub.models.Login;
import com.example.indonesiainvestorclub.models.Profile;

import java.util.List;

public class ProfileRes {
    private Login login;
    private Profile profile;
    private Bank bank;
    private Documents documents;

    public ProfileRes(Login login, Profile profile, Bank bank,
        Documents documents) {
        this.login = login;
        this.profile = profile;
        this.bank = bank;
        this.documents = documents;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }
}
