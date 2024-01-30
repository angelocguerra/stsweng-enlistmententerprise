package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Objects;

/**
 * Represents a room where classes would be held, with a maximum capacity.
 */
class Room {
    private final String roomName;
    private final int maxCapacity;

    /**
     * Creates a new room with a specified name and maximum capacity.
     * @param roomName      The name of the room.
     * @param maxCapacity   The maximum capacity of the room.
     */
    Room(String roomName, int maxCapacity) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was: " + roomName);
        isTrue(maxCapacity > 0, "maxCapacity must be greater than 0, was: " + maxCapacity);
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Checks if the number of enrolled students exceeds the maximum capacity of the room.
     * @param numberOfEnlisted  The number of students enrolled in the associated section.
     */
    void checkForOverCapacity(int numberOfEnlisted) {
        if (numberOfEnlisted >= maxCapacity) {
            throw new RoomCapacityReachedException("Room " + this + " has reached max capacity of " + maxCapacity);
        }
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Room room = (Room) o;

        return Objects.equals(roomName, room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, maxCapacity);
    }
}
