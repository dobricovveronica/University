package com.amsoftgroup.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person{

    private Group group;

    private Set<Discipline> disciplines;

    private HashSet<Average> averages;

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

    public HashSet<Average> getAverages() {
        return averages;
    }

    public void setAverages(HashSet<Average> averages) {
        this.averages = averages;
    }
}
