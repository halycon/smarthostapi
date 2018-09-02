package com.halycon.smarthostapi.service.impl;

import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import com.halycon.smarthostapi.service.IOptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("OccupancyOptimizationService")
public class OccupancyOptimizationService implements IOptimizationService<OccupancyOptimizationRequest, OccupancyOptimizationResponse> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OccupancyOptimizationResponse optimize(OccupancyOptimizationRequest occupancyOptimizationRequest) throws Exception {

        return null;
    }

}
