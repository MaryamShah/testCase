package com.tbagroup.service;

public interface CraneService {
    public void setMoveService(MoveService moveService);
    public void setTaskService(TaskService taskService);
    public void startWork();
    public void stopWork();
}
