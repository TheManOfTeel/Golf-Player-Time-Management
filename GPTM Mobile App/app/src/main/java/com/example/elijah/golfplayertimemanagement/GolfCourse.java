package com.example.elijah.golfplayertimemanagement;

public class GolfCourse {
    String ID;
    String name;

    public GolfCourse() {
    }

    public GolfCourse(String ID, String name) {
        this.ID = ID;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "GolfCourse{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
