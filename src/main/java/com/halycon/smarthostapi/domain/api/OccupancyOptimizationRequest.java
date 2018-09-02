package com.halycon.smarthostapi.domain.api;

import com.halycon.smarthostapi.domain.RoomStock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OccupancyOptimizationRequest implements Serializable {

    private static final long serialVersionUID = 2390079120567201002L;
    private List<RoomStock> roomStockList;
    private BigDecimal[] prices;

    public List<RoomStock> getRoomStockList() {
        return roomStockList;
    }

    public void setRoomStockList(List<RoomStock> roomStockList) {
        this.roomStockList = roomStockList;
    }

    public BigDecimal[] getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal[] prices) {
        this.prices = prices;
    }
}
