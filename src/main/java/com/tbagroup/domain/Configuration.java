package com.tbagroup.domain;

import com.tbagroup.Application;

public class Configuration {

    private final String name;
    private final int length;
    private final int craneCount;

    public Configuration(String name, int length, int craneCount) {
        this.name = name;
        this.length = length;
        this.craneCount = craneCount;
        if(craneCount < 0 || craneCount > 2){
            String msg = "we do not support more than 2 cranes or less than 1 crane";
            Application.LOGGER.error(msg);
            throw new TrackValidationException(msg);
        }
        if(length <= 0 ){
            String msg = "length of track must be greater than 0";
            Application.LOGGER.error(msg);
            throw new TrackValidationException(msg);
        }
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getCraneCount() {
        return craneCount;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", craneCount=" + craneCount +
                '}';
    }
}
