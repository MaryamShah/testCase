package com.tbagroup.service.impl;

import com.tbagroup.Application;
import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.domain.dto.TaskDto;
import com.tbagroup.service.TaskService;

import java.util.Random;

public class TaskServiceImpl implements TaskService {

    private Integer maxLength;

    public TaskServiceImpl(Integer maxLength) {
        this.maxLength = maxLength;

    }

    @Override
    public void produceTask(CraneDto crane) {
        Thread thread = new Thread(new TaskProducer(crane));
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * try to generate randomly movement tasks between crane park position and entered maxLength
     */
    private static TaskDto createDummyTask(Integer parkPosition, Integer maxLength) {
        TaskDto taskDto = null;
        Random random = new Random();
        int last = random.ints(1, maxLength).findFirst().getAsInt() + 1;
        int first = random.ints(1, last).findFirst().getAsInt();
        if(parkPosition > maxLength){
            taskDto = new TaskDto(last ,first);
        }else {
            taskDto = new TaskDto(first, last);
        }
        return taskDto;
    }

    public class TaskProducer implements Runnable {

        private CraneDto crane;

        public TaskProducer(CraneDto crane) {
            this.crane = crane;
        }

        /**
         * generate a random task for it's related crane each 6 milliseconds by calling createDummyTask method
         */
        @Override
        public void run() {
            while (true) {
                TaskDto dto = TaskServiceImpl.createDummyTask(crane.getParkPosition(),maxLength);
                crane.getTasks().offer(dto);
                Application.LOGGER.info("task {} is  generated for crane {}", dto, crane.getId());
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
