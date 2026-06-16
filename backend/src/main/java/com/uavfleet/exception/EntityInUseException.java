package com.uavfleet.exception;

/** Thrown when deleting an entity is blocked by an existing reference (e.g. an assignment). */
public class EntityInUseException extends RuntimeException {
    public EntityInUseException(String message) {
        super(message);
    }
}
