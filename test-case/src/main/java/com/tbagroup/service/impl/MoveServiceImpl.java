package com.tbagroup.service.impl;

import com.tbagroup.Application;
import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.domain.dto.TaskDto;
import com.tbagroup.domain.dto.TrackDto;
import com.tbagroup.service.MoveService;

public class MoveServiceImpl implements MoveService {

    private TrackDto track;

    public MoveServiceImpl(TrackDto track) {
        this.track = track;
    }

    @Override
    public synchronized void move(CraneDto crane) {
        TaskDto peek = crane.getTasks().peek();
        if (peek != null) {
            handler(peek, crane);
            crane.getTasks().poll();
            Application.LOGGER.info("task {} is done", peek.getId());
        }
    }

    private void handler(TaskDto peek, CraneDto crane) {
        CraneDto other = track.getOtherCrane(crane);
        if (other != null) {
            TaskDto motion = null;
            int position = peek.getDestination();
            if ((peek.getDestination() >= other.getPosition()
                    && crane.getParkPosition() == 0)) {
                position++;
                if (position == other.getParkPosition() - 1) {
                    position = other.getParkPosition();
                }

            } else if (peek.getDestination() <= other.getPosition()
                    && crane.getParkPosition() != 0) {
                position--;
                if (position == other.getParkPosition() + 1) {
                    position = other.getParkPosition();
                }
            }
            if (other.isInPark()) {
                position = other.getParkPosition();
            }
            motion = new TaskDto(other.getId(), "Crane-Move", other.getPosition(), position);
            doIt(motion, other);
        }
        doIt(peek, crane);
    }

    private void doIt(TaskDto poll, CraneDto crane) {
        if (poll.getCurrentPosition() == poll.getDestination()) {
            Application.LOGGER.info("no movement for crane {} type {} from {} , to {} "
                    , poll.getId(), poll.getType(), poll.getCurrentPosition(), poll.getDestination());
        } else {
            Application.LOGGER.info("move crane {} type {} from {} , to {} "
                    , poll.getId(), poll.getType(), poll.getCurrentPosition(), poll.getDestination());
            crane.setPosition(poll.getDestination());
        }
        Application.LOGGER.info("current crane {} position [{}-{}] "
                , crane.getId(), crane.getParkPosition(), crane.getPosition());
    }

}
