package com.rain.flowers;

/**
 * @author sourc
 * @date 2018/6/10
 */
public class Plant {
    /**名称*/
    private String name;
    /**形态*/
    private String looks;
    /**习性*/
    private String habits;
    /**产地*/
    private String origin;
    /**栽培技巧*/
    private String grow;

    public Plant() {}

    public Plant(String name, String looks, String habits, String origin, String grow) {
        this.name = name;
        this.looks = looks;
        this.habits = habits;
        this.origin = origin;
        this.grow = grow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLooks() {
        return looks;
    }

    public void setLooks(String looks) {
        this.looks = looks;
    }

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGrow() {
        return grow;
    }

    public void setGrow(String grow) {
        this.grow = grow;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", looks='" + looks + '\'' +
                ", habits='" + habits + '\'' +
                ", origin='" + origin + '\'' +
                ", grow='" + grow + '\'' +
                '}';
    }
}