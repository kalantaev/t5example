package com.example.t5.entities.view;

import com.example.t5.data.Units;

/**
 * Created by Калантаев Александр on 02.03.2017.
 */
public class Storage {
    private Long id;
    private String name;
    private String code;
    private double count;
    private Units units;

    public Storage(Long id, String name, String code, double count, Units units) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.count = count;
        this.units = units;
    }

    public Storage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
