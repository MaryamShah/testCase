package com.tbagroup.service.impl;

import com.tbagroup.Application;
import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.domain.dto.TrackDto;
import com.tbagroup.service.CraneService;
import com.tbagroup.service.MoveService;
import com.tbagroup.service.TaskService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class CraneServiceImpl implements CraneService {

    private TaskService taskService;
    private TrackDto track;
    private ExecutorService executor;
    private MoveService moveService;

    public CraneServiceImpl(TrackDto track) {
        this.track = track;
        this.taskService = new TaskServiceImpl(track.getLength());
        this.moveService = new MoveServiceImpl(track);
    }

    @Override
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void setMoveService(MoveService moveService) {
        this.moveService = moveService;
    }

    /**
     * Creates a thread pool by fixed number of threads and start to pick one by one movement task to do
     */
    @Override
    public void startWork() {
        setupCranes();
        executor = Executors.newFixedThreadPool(track.getCraneCount());
        for (CraneDto dto : track.getRelatedCranes()) {
            executor.execute(new CraneMovement(dto, track));
        }
    }

    @Override
    public void stopWork() {
        if (executor != null && !executor.isShutdown())
            executor.shutdownNow();
    }

    /**
     * set up crane(s) as much as track craneCount says,
     * first put them in the parking, then produce tasks randomly
     */
    private void setupCranes() {
        int park = track.getLeftParking();
        for (int i = 0; i < track.getCraneCount(); i++) {
            CraneDto crane = new CraneDto(park);
            crane.addTasks(new PriorityBlockingQueue<>());
            track.addCrane(crane);
            taskService.produceTask(crane);
            Application.LOGGER.info("crane {} initialized in parking {}..."
                    , crane.getId(), crane.getPosition());
            park = track.getRightParking();
        }
    }

    public class CraneMovement implements Runnable {

        private volatile TrackDto track;
        private volatile CraneDto crane;

        public CraneMovement(CraneDto crane, TrackDto track) {
            this.crane = crane;
            this.track = track;
        }

        @Override
        public void run() {
            while (true) {
                moveService.move(crane);
            }
        }

    }

}
