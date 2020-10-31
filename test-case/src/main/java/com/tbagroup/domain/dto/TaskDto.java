package com.tbagroup.domain.dto;

import java.util.Objects;
import java.util.UUID;

public class TaskDto implements Comparable<TaskDto> {

    private final UUID id;
    private final String type;
    private Integer currentPosition;
    private Integer destination;
    private Integer priority;

    public TaskDto(Integer startPosition, Integer destination) {
        this.id = UUID.randomUUID();
        this.type = "Crane-Move";
        this.currentPosition = startPosition;
        this.destination = destination;
        this.priority = 0;
    }

    public TaskDto(UUID id,String type, Integer startPosition, int destination) {
        this.id = id;
        this.type = type;
        this.currentPosition = startPosition;
        this.destination = destination;
        this.priority = 5;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(TaskDto o) {
        return this.priority.compareTo(o.priority);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", startPosition=" + currentPosition +
                ", endPosition=" + destination +
                ", priority=" + priority +
                '}';
    }
}
