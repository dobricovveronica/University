package com.amsoftgroup.model;

import java.time.LocalDate;
import java.util.HashSet;

public class Mark {

    private long id;

    private double value;

    private LocalDate createData;

    private Discipline discipline;

    private Teacher teacher;

    private Student student;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getCreateData() {
        return createData;
    }

    public void setCreateData(LocalDate createData) {
        this.createData = createData;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
