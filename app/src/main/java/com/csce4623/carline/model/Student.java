package com.csce4623.carline.model;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("grade")
    private int grade;
    @SerializedName("room")
    private int room;
    @SerializedName("cars")// cars are just strings representing license plates for now
    private String[] cars;

    public Student(String id, String name, int grade, int room, String[] cars) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.room = room;
        this.cars = cars;
    }

    public String getId() {
        return id;
    }

    public Student setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public int getGrade() {
        return grade;
    }

    public Student setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public Student setRoom(int room) {
        this.room = room;
        return this;
    }

    public String[] getCars() {
        return cars;
    }

    public Student setCars(String[] cars) {
        this.cars = cars;
        return this;
    }
}
