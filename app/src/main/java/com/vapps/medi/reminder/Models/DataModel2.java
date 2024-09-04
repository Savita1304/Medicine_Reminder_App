package com.vapps.medi.reminder.Models;

public class DataModel2 {
    String id,date,time,medicationName,Status,type,startTime,frequency,username,userid;


    public DataModel2() {
    }

    public DataModel2(String date, String medicationName, String status, String time) {
        this.date = date;
        this.medicationName = medicationName;
        this.Status = status;
        this.time = time;
    }

    public DataModel2(String id, String date, String time, String medicationName, String status, String startTime, String frequency,String username,String userid)
    {
        this.id = id;
        this.date = date;
        this.time = time;
        this.medicationName = medicationName;
        this.Status = status;
        this.startTime=startTime;
        this.frequency = frequency;
        this.username = username;
        this.userid = userid;


    }






  /*  public DataModel2(String id, String date, String time, String medicationName, String status, String type, String startTime, String frequency,String userid) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.medicationName = medicationName;
        Status = status;
        this.type = type;
        this.startTime=startTime;
        this.frequency = frequency;

        this.userid = userid;


    }*/




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
