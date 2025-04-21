package com.instagrom.instagrom.models.util;

public enum RelationStatus {
    PENDING(0),
    ACCEPTED(1),
    BLOCKED(2);

    private final int value;

    RelationStatus(int value) {
        this.value = value;
    }

    public String getName() {
        return this.name();
    }

    public int getValue() {
        return value;
    }
}
