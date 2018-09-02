package com.halycon.smarthostapi.domain.api;

import com.halycon.smarthostapi.domain.RoomPriceSummary;

import java.io.Serializable;
import java.util.List;

public class OccupancyOptimizationResponse implements Serializable {

    private static final long serialVersionUID = 8429197634422354048L;
    private boolean success;
    private List<RoomPriceSummary> priceSummaries;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<RoomPriceSummary> getPriceSummaries() {
        return priceSummaries;
    }

    public void setPriceSummaries(List<RoomPriceSummary> priceSummaries) {
        this.priceSummaries = priceSummaries;
    }

    @Override
    public String toString() {
        return "OccupancyOptimizationResponse{" +
                "success=" + success +
                ", priceSummaries=" + priceSummaries +
                '}';
    }
}
