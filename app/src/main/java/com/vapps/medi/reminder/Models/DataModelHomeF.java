package com.vapps.medi.reminder.Models;

public class DataModelHomeF {
    String type,id,medicationName,decName,shape,frequency,time1,time2,hours,week,time,alarmType,userName,userid,meddate;
int noofDays,leftpills,notify,fdays,fpills ;
boolean open;
  // DataModelHomeF(mediName, decName, Shape,Freq, time1, time2, time3, alarmType);


    public DataModelHomeF() {
    }

    public DataModelHomeF(String id, String medicationName, String userName) {
        this.id = id;
        this.medicationName = medicationName;
        this.userName=userName;
    }

    public DataModelHomeF(String id, String medicationName, String userName,int leftpill) {
        this.id = id;
        this.medicationName = medicationName;
        this.userName=userName;
        this.leftpills = leftpill;
    }

    public DataModelHomeF(String id, String medicationName, String decName, String shape, String frequency, String time1, String time2, String hours, String week, String time, int noofDays, String alarmType, int leftpills, int notify, String type, String userName,boolean open,String userid,String meddate,int fdays,int fpills) {
        this.id = id;
        this.medicationName = medicationName;
        this.decName = decName;
        this.shape = shape;
        this.frequency = frequency;
        this.time1 = time1;
        this.time2 = time2;
        this.noofDays = noofDays;
        this.hours = hours;
        this.week = week;
        this.time = time;

        this.alarmType = alarmType;
        this.leftpills = leftpills;
        this.notify = notify;
        this.type=type;
        this.userName=userName;
        this.open = open;
        this.userid = userid;
        this.meddate = meddate;
        this.fdays = fdays;
        this.fpills = fpills;
    }


    public int getFpills() {
        return fpills;
    }

    public void setFpills(int fpills) {
        this.fpills = fpills;
    }

    public int getFdays() {
        return fdays;
    }

    public void setFdays(int fdays) {
        this.fdays = fdays;
    }

    public String getMeddate() {
        return meddate;
    }

    public void setMeddate(String meddate) {
        this.meddate = meddate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public int  getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(int noofDays) {
        this.noofDays = noofDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDecName() {
        return decName;
    }

    public void setDecName(String decName) {
        this.decName = decName;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }



    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public int getLeftpills() {
        return leftpills;
    }

    public void setLeftpills(int leftpills) {
        this.leftpills = leftpills;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}




