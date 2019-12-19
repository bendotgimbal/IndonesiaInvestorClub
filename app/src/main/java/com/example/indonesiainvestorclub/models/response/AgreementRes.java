package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Agreement;

import java.util.List;

public class AgreementRes {
    private List<Agreement> agreement;

    public AgreementRes(List<Agreement> agreement) {
        this.agreement = agreement;
    }

    public List<Agreement> getAgreement() {
        return agreement;
    }

    public void setAgreement(List<Agreement> agreement) {
        this.agreement = agreement;
    }
}
