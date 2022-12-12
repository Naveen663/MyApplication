package com.text.myapplication.entities;

public class SubDistrict {

    private int subdisId;
    private String subdisName;
    private int disID;

    public SubDistrict() {
    }

    public SubDistrict(int subdisId, String subdisName, int disID) {
        this.subdisId = subdisId;
        this.subdisName = subdisName;
        this.disID = disID;
    }

    public int getSubdisId() {
        return subdisId;
    }

    public void setSubdisId(int subdisId) {
        this.subdisId = subdisId;
    }

    public String getSubdisName() {
        return subdisName;
    }

    public void setSubdisName(String subdisName) {
        this.subdisName = subdisName;
    }

    public int getDisID() {
        return disID;
    }

    public void setDisID(int disID) {
        this.disID = disID;
    }
}
