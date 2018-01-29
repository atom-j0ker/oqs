package com.oqs.model;

public enum VisitStatus {
    VISITED("VISITED"),
    NOT_VISITED("NOT_VISITED"),
    WAITING("WAITING");

    private String value;

    VisitStatus(String string) {
        value = string;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
