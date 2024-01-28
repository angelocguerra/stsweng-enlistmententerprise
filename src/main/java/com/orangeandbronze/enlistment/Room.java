package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.Validate.*;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.*;

public class Room {
    private final String roomName;
    private final int maxCapacity;

    Room(String roomName, int maxCapacity) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was: " + roomName);
        isTrue(maxCapacity > 0, "maxCapacity must be greater than 0, was: " + maxCapacity);
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
    }

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
