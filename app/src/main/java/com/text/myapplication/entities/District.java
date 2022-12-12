package com.text.myapplication.entities;

public class District {
    private int disID;
    private int stID;
    private String disName;

    public District() {
    }

    public District(int disID, String disName) {
        this.disID = disID;
        this.disName = disName;
    }

    public District(int disID, int stID, String disName) {
        this.disID = disID;
        this.stID = stID;
        this.disName = disName;
    }

    public int getDisID() {
        return disID;
    }

    public void setDisID(int disID) {
        this.disID = disID;
    }

    public int getStID() {
        return stID;
    }

    public void setStID(int stID) {
        this.stID = stID;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }
}
