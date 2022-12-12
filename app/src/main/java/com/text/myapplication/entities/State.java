package com.text.myapplication.entities;

public class State {
    private int stid;
    private String stateName;

    public State() {
    }

    public State(int stid, String stateName) {
        this.stid = stid;
        this.stateName = stateName;
    }

    public int getStid() {
        return stid;
    }

    public void setStid(int stid) {
        this.stid = stid;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
