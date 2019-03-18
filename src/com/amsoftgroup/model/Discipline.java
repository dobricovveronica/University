package com.amsoftgroup.model;

import java.util.HashSet;

public class Discipline {

    private long id;

    private String title;

    private Teacher teachers;

    private HashSet<Mark> marks;

    private HashSet<Average> averages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teacher getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher teachers) {
        this.teachers = teachers;
    }

    public HashSet<Mark> getMarks() {
        return marks;
    }

    public void setMarks(HashSet<Mark> marks) {
        this.marks = marks;
    }

    public HashSet<Average> getAverages() {
        return averages;
    }

    public void setAverages(HashSet<Average> averages) {
        this.averages = averages;
    }
}
