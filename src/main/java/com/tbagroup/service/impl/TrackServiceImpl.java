package com.tbagroup.service.impl;

import com.tbagroup.Application;
import com.tbagroup.domain.Configuration;
import com.tbagroup.domain.dto.TrackDto;
import com.tbagroup.service.CraneService;
import com.tbagroup.service.TrackService;

public class TrackServiceImpl implements TrackService {

    private TrackDto track;
    private CraneService craneService;

    public TrackServiceImpl(Configuration configuration) {
        this.track = new TrackDto(configuration.getName(), configuration.getLength(), configuration.getCraneCount());
        this.craneService = new CraneServiceImpl(this.track);
        Application.LOGGER.info("Track {} initialized...",track.getName());
    }


    @Override
    public void setCraneService(CraneService craneService) {
        this.craneService = craneService;
    }

    @Override
    public void startWork() {
        craneService.startWork();
    }

    @Override
    public void stopWork() {
        craneService.stopWork();
    }


}
