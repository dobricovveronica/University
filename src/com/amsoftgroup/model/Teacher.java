package com.amsoftgroup.model;

import java.util.HashSet;

public class Teacher extends Person{
    private long id;

    private double salary;

    private HashSet<Discipline> disciplines;

    private HashSet<Mark> marks;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public HashSet<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(HashSet<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public HashSet<Mark> getMarks() {
        return marks;
    }

    public void setMarks(HashSet<Mark> marks) {
        this.marks = marks;
    }

}
