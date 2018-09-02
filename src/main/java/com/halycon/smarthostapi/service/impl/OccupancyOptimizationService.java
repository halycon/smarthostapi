package com.halycon.smarthostapi.service.impl;

import com.halycon.smarthostapi.domain.RoomStock;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import com.halycon.smarthostapi.service.IOptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("OccupancyOptimizationService")
public class OccupancyOptimizationService implements IOptimizationService<OccupancyOptimizationRequest, OccupancyOptimizationResponse> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OccupancyOptimizationResponse optimize(OccupancyOptimizationRequest occupancyOptimizationRequest) throws Exception {

        validateOccupancyOptimizationRequest(occupancyOptimizationRequest);

        OccupancyOptimizationResponse response = new OccupancyOptimizationResponse();

        Arrays.parallelSort(occupancyOptimizationRequest.getPrices());

        occupancyOptimizationRequest.getRoomStockList().sort(
                (RoomStock r1, RoomStock r2) -> r2.getMinPrice().compareTo(r1.getMinPrice()));

        return response;
    }

    public void validateOccupancyOptimizationRequest(OccupancyOptimizationRequest occupancyOptimizationRequest) throws Exception{
        if(occupancyOptimizationRequest.getRoomStockList()==null || occupancyOptimizationRequest.getRoomStockList().size()==0)
            throw new IllegalArgumentException("RoomStock definitions missing!");
        if(occupancyOptimizationRequest.getPrices()==null || occupancyOptimizationRequest.getPrices().length==0)
            throw new IllegalArgumentException("Prices missing!");

        validateRoomStockDefinitions(occupancyOptimizationRequest.getRoomStockList());
    }

    public void validateRoomStockDefinitions(List<RoomStock> roomStockList){
        for(RoomStock roomStock : roomStockList) {
            if(roomStock.getRoomType()==null)
                throw new IllegalArgumentException("RoomType not defined in RoomStock :: "+roomStock.toString());
        }
    }
}
