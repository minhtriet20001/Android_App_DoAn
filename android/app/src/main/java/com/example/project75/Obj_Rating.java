package com.example.project75;

public class Obj_Rating {
    String name;
    String rate;
    String comment;
    String time;

    public Obj_Rating(String name, String rate, String comment, String time) {
        this.name = name;
        this.rate = rate;
        this.comment = comment;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
