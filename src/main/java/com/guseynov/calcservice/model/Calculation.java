package com.guseynov.calcservice.model;

public class Calculation {

    private String operationType;
    private Double value;


    public String getOperationType() {
        return operationType;
    }

    public Double getValue() {
        return value;
    }

    public Calculation() {

    }

    public Calculation(String operationType, Double value) {
        this.operationType = operationType;
        this.value = value;
    }
}
