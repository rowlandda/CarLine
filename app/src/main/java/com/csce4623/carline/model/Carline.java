package com.csce4623.carline.model;

import java.util.ArrayList;

public class Carline {
    private ArrayList<Student> allStudents;
    private ArrayList<LineStudent> lineStudents;

    public Carline() {
        this.allStudents = new ArrayList<>();
        this.lineStudents = new ArrayList<>();
    }

    public void moveUpOne(LineStudent student) {
    }

    public void moveDownOne(LineStudent student) {
    }

    public void changePosition(LineStudent student, int pos) {
        lineStudents.add(pos, student);
    }
}
