package com.halycon.smarthostapi.controller;

import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import com.halycon.smarthostapi.service.IOptimizationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class InsightsController {

    @Resource(name = "OccupancyOptimizationService")
    private IOptimizationService<OccupancyOptimizationRequest, OccupancyOptimizationResponse> occupancyOptimizationService;

    @RequestMapping(value = "/getOptimizedOccupancySummary", method = {RequestMethod.POST} , produces = "application/json")
    private OccupancyOptimizationResponse getOptimizedOccupancySummary(@RequestBody OccupancyOptimizationRequest occupancyOptimizationRequest) throws Exception{
        return occupancyOptimizationService.optimize(occupancyOptimizationRequest);
    }
}
