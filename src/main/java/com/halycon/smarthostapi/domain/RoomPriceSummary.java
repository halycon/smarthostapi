package com.halycon.smarthostapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomPriceSummary implements Serializable {

    private static final long serialVersionUID = -3845533420508961227L;
    private BigDecimal totalPrice;
    private int allocation;
    private RoomType roomType;

    public RoomPriceSummary() {
    }

    public RoomPriceSummary(BigDecimal totalPrice, int allocation, RoomType roomType) {
        this.totalPrice = totalPrice;
        this.allocation = allocation;
        this.roomType = roomType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    public void incrementAllocation(){
        this.allocation++;
    }

    public void addTotalPrice(BigDecimal value){
        this.totalPrice = this.totalPrice.add(value);
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "RoomPriceSummary{" +
                "totalPrice=" + totalPrice +
                ", allocation=" + allocation +
                ", roomType=" + roomType +
                '}';
    }
}
