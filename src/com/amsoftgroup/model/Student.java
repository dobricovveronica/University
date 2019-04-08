package com.amsoftgroup.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person {

    private Group group;

    private Set<Discipline> disciplines;

    private Average average;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Average getAverage() {
        return average;
    }

    public void setAverage(Average average) {
        this.average = average;
    }
}
