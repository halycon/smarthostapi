package com.halycon.smarthostapi.service.impl;

import com.halycon.smarthostapi.domain.RoomPriceSummary;
import com.halycon.smarthostapi.domain.RoomStock;
import com.halycon.smarthostapi.domain.RoomType;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import com.halycon.smarthostapi.service.IOptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<RoomType,RoomPriceSummary> priceSummaryMap = new HashMap<>();

        occupancyOptimizationRequest.getRoomStockList().forEach(i -> priceSummaryMap.put(i.getRoomType(), new RoomPriceSummary(BigDecimal.ZERO,0,i.getRoomType())));

        int startingAllocation = occupancyOptimizationRequest.getRoomStockList().get(0).getAllocation();
        int roomTypeIndx = 0;

        BigDecimal thresholdValue = occupancyOptimizationRequest.
                getPrices()[occupancyOptimizationRequest.getPrices().length-1].add(BigDecimal.ONE);


        for (int i = occupancyOptimizationRequest.getPrices().length-1 ; i >= 0 ; i--) {

            if(roomTypeIndx > occupancyOptimizationRequest.getRoomStockList().size())
                break;

            BigDecimal price = occupancyOptimizationRequest.getPrices()[i];
            RoomStock roomStock = occupancyOptimizationRequest.getRoomStockList().get(roomTypeIndx);

            if(thresholdValue.compareTo(price)<0)
                continue;

            if(startingAllocation>0 && price.compareTo(roomStock.getMinPrice())>=0){
                priceSummaryMap.get(roomStock.getRoomType()).incrementAllocation();
                priceSummaryMap.get(roomStock.getRoomType()).addTotalPrice(price);
                occupancyOptimizationRequest.getPrices()[i] = BigDecimal.ZERO;
                startingAllocation--;
                occupancyOptimizationRequest.getRoomStockList().get(roomTypeIndx).setAllocation(startingAllocation);

                if(startingAllocation==0 || (i-1>=0 && occupancyOptimizationRequest.getPrices()[i-1].compareTo(roomStock.getMinPrice())<0 && startingAllocation>0)) {
                    thresholdValue = roomStock.getMinPrice().subtract(BigDecimal.ONE);
                    roomTypeIndx++;

                    if(roomTypeIndx > occupancyOptimizationRequest.getRoomStockList().size()-1)
                        break;

                    startingAllocation = occupancyOptimizationRequest.getRoomStockList().get(roomTypeIndx).getAllocation();

                }
            }
        }

        response.setSuccess(true);

        response.setPriceSummaries(priceSummaryMap.entrySet().stream()
                .filter(i-> i.getValue().getAllocation()>0)
                .map(i-> i.getValue())
                .collect(Collectors.toList()));

        logger.info(" response :: {} ", response);

        return response;
    }

    private void validateOccupancyOptimizationRequest(OccupancyOptimizationRequest occupancyOptimizationRequest) throws IllegalArgumentException{
        if(occupancyOptimizationRequest.getRoomStockList()==null || occupancyOptimizationRequest.getRoomStockList().size()==0)
            throw new IllegalArgumentException("RoomStock definitions missing!");
        if(occupancyOptimizationRequest.getPrices()==null || occupancyOptimizationRequest.getPrices().length==0)
            throw new IllegalArgumentException("Prices missing!");

        validateRoomStockDefinitions(occupancyOptimizationRequest.getRoomStockList());
    }

    private void validateRoomStockDefinitions(List<RoomStock> roomStockList){
        for(RoomStock roomStock : roomStockList) {
            if(roomStock.getRoomType()==null)
                throw new IllegalArgumentException("RoomType not defined in RoomStock :: "+roomStock.toString());
        }
    }
}
