package com.vapps.medi.reminder.Models;

public class DataModelHistory {
    String currentTime,imgg, mediName,decccN;

    public DataModelHistory() {
    }

    public DataModelHistory(String currentTime, String imgg, String mediName, String decccN) {
        
        this.currentTime = currentTime;
        this.imgg = imgg;
        this.mediName = mediName;
        this.decccN = decccN;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getImgg() {
        return imgg;
    }

    public void setImgg(String imgg) {
        this.imgg = imgg;
    }

    public String getMediName() {
        return mediName;
    }

    public void setMediName(String mediName) {
        this.mediName = mediName;
    }

    public String getDecccN() {
        return decccN;
    }

    public void setDecccN(String decccN) {
        this.decccN = decccN;
    }


}

