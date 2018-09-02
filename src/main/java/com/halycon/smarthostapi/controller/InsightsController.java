package com.halycon.smarthostapi.controller;

import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import com.halycon.smarthostapi.service.IOptimizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(value = "/", description = "Optimization Insights API")
public class InsightsController {

    @Resource(name = "OccupancyOptimizationService")
    private IOptimizationService<OccupancyOptimizationRequest, OccupancyOptimizationResponse> occupancyOptimizationService;

    @ApiOperation(value = "Operation for getting occupancy optimization insight",
            notes = "RoomStock criterias and test prices has to be provided together",
            response = OccupancyOptimizationResponse.class)
    @RequestMapping(value = "/getOptimizedOccupancySummary", method = {RequestMethod.POST} , produces = "application/json")
    private OccupancyOptimizationResponse getOptimizedOccupancySummary(@RequestBody OccupancyOptimizationRequest occupancyOptimizationRequest) throws Exception{
        return occupancyOptimizationService.optimize(occupancyOptimizationRequest);
    }
}
