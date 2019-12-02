package com.csce4623.carline.model;

import com.google.gson.annotations.SerializedName;

public class LineStudent {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("grade")
    private int grade;
    @SerializedName("room")
    private int room;
    @SerializedName("cars")// cars are just strings representing license plate
    private String[] cars;
    @SerializedName("position")
    private int position;

    public LineStudent(String id, String name, int grade, int room, String[] cars, int position) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.room = room;
        this.cars = cars;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public LineStudent setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LineStudent setName(String name) {
        this.name = name;
        return this;
    }

    public int getGrade() {
        return grade;
    }

    public LineStudent setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public LineStudent setRoom(int room) {
        this.room = room;
        return this;
    }

    public String[] getCars() {
        return cars;
    }

    public LineStudent setCars(String[] cars) {
        this.cars = cars;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public LineStudent setPosition(int position) {
        this.position = position;
        return this;
    }
}
