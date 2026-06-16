package com.uavfleet.exception;

public class NoEligibleUavException extends RuntimeException {
    public NoEligibleUavException(String message) {
        super(message);
    }
}
