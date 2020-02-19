package com.project.indonesiainvestorclub.models.response;

public class UpdateImageProofOfIDRes {
    private String upload;
    private String proof_bank;

    public UpdateImageProofOfIDRes(String upload, String proof_bank) {
        this.upload = upload;
        this.proof_bank = proof_bank;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getProofBank() {
        return proof_bank;
    }

    public void setProofBank(String proof_bank) {
        this.proof_bank = proof_bank;
    }
}