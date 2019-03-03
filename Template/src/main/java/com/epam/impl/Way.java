package com.epam.impl;

public class Way {


    private String pointOfDeparture;

    private int length;

    private String destinationPoint;

    private int costOfTheWay;


    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getCostOfTheWay() {
        return costOfTheWay;
    }

    public void setCostOfTheWay(int costOfTheWay) {
        this.costOfTheWay = costOfTheWay;
    }

    @Override
    public String toString() {
        return "Way{" +
                "pointOfDeparture='" + pointOfDeparture + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", length=" + length +
                ", costOfTheWay=" + costOfTheWay +
                '}'+'\n';
    }

}
