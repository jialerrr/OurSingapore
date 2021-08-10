package rp.edu.sg.c346.id20021576.oursingapore;

import java.io.Serializable;

public class Island implements Serializable {

    private int id;
    private String name;
    private String desc;
    private int islandArea;
    private float stars;

    public Island(String name, String desc, int islandArea, float stars) {
        this.name = name;
        this.desc = desc;
        this.islandArea = islandArea;
        this.stars = stars;
    }

    public Island(int id, String name, String desc, int islandArea, float stars) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.islandArea = islandArea;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Island setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Island setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Island setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getislandArea() {
        return islandArea;
    }

    public Island setislandArea(int islandArea) {
        this.islandArea = islandArea;
        return this;
    }

    public float getStars() {
        return stars;
    }

    public Island setStars(float stars) {
        this.stars = stars;
        return this;
    }
}