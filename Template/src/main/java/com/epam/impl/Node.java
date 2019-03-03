package com.epam.impl;

import java.util.ArrayList;
import java.util.HashSet;

public class Node {


    private String name;

    private String pointOfDeparture;

    private ArrayList<String> destinationPoints;
    private HashSet<String> pointsOfDepartures;

    private int weight;

    private int costFromPointOfDeparture;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public ArrayList<String> getDestinationPoints() {
        return destinationPoints;
    }

    public void setDestinationPoints(ArrayList<String> destinationPoints) {
        this.destinationPoints = destinationPoints;
    }

    public int getCostFromPointOfDeparture() {
        return costFromPointOfDeparture;
    }

    public void setCostFromPointOfDeparture(int costFromPointOfDeparture) {
        this.costFromPointOfDeparture = costFromPointOfDeparture;
    }


    public HashSet<String> getPointsOfDepartures() {
        return pointsOfDepartures;
    }

    public void setPointsOfDepartures(HashSet<String> pointsOfDepartures) {
        this.pointsOfDepartures = pointsOfDepartures;
    }


    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", pointOfDeparture='" + pointOfDeparture + '\'' +
                ", destinationPoints=" + destinationPoints +
                ", pointsOfDepartures=" + pointsOfDepartures +
                ", isChecked=" + isChecked +
                ", weight=" + weight +
                ", costFromPointOfDeparture=" + costFromPointOfDeparture +
                '}' + '\n';
    }



}
