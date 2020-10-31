package com.tbagroup.domain.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TrackDto {
    private final String name;
    private final Integer length;
    private final Integer rightParking;
    private final Integer leftParking;
    private final Integer craneCount;
    private Set<CraneDto> relatedCranes; // maximum number is 2

    public TrackDto(String name, Integer length, int craneCount) {
        this.name = name;
        this.length = length;
        this.leftParking = 0;
        this.rightParking = length + 1;
        this.craneCount = craneCount;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getRightParking() {
        return rightParking;
    }

    public Integer getLeftParking() {
        return leftParking;
    }

    public Integer getCraneCount() {
        return craneCount;
    }

    public Set<CraneDto> getRelatedCranes() {
        if(relatedCranes == null){
            relatedCranes = new HashSet<>();
        }
        return relatedCranes;
    }

    public void addCrane(CraneDto crane) {
        getRelatedCranes().add(crane);
    }

    public CraneDto getOtherCrane(CraneDto crane) {
        CraneDto other = null;
        for(CraneDto dto : getRelatedCranes()){
            if(!dto.equals(crane))
                other = dto;
        }
        return other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackDto trackDto = (TrackDto) o;
        return Objects.equals(name, trackDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TrackDto{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", rightParking=" + rightParking +
                ", leftParking=" + leftParking +
                ", craneCount=" + craneCount +
                '}';
    }
}
