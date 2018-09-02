package com.halycon.smarthostapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomStock implements Serializable {

    private static final long serialVersionUID = 3623696916677013811L;
    private RoomType roomType;
    private BigDecimal minPrice;
    private int allocation;

    public RoomStock(){

    }

    public RoomStock(RoomType roomType, BigDecimal minPrice, int allocation) {
        this.roomType = roomType;
        this.minPrice = minPrice;
        this.allocation = allocation;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public int getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    @Override
    public String toString() {
        return "RoomStock{" +
                "roomType=" + roomType +
                ", minPrice=" + minPrice +
                ", allocation=" + allocation +
                '}';
    }
}
