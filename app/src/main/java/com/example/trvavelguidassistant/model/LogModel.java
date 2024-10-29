package com.example.trvavelguidassistant.model;


public class LogModel {

    String date="";
    String name="";
    int num= 0;
    String ds = "";

    public LogModel(String date) {
        this.date = date;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public LogModel(String date, String name, int num,String ds) {
        this.date = date;
        this.name = name;
        this.num = num;
        this.ds = ds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
