package com.project.indonesiainvestorclub.models.response;

public class UpdateImageProofOfBankRes {
    private String upload;
    private String proof_id;

    public UpdateImageProofOfBankRes(String upload, String proof_id) {
        this.upload = upload;
        this.proof_id = proof_id;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getProofID() {
        return proof_id;
    }

    public void setProofID(String proof_id) {
        this.proof_id = proof_id;
    }
}