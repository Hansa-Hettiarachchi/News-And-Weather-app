package com.example.trvavelguidassistant.model;

public class EventModel {
    private String Title;
    private String Description;
    private String Start;
    private String End;

    public EventModel() {
    }

    public EventModel(String title, String description, String start, String end) {
        Title = title;
        Description = description;
        Start = start;
        End = end;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }
}
